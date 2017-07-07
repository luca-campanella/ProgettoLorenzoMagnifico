package it.polimi.ingsw.server;

import it.polimi.ingsw.choices.NetworkChoicesPacketHandler;
import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.controller.ModelController;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.LeadersDeck;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.client.network.socket.packet.PlayerPositionEndGamePacket;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * the controller of the game on the server
 */
public class ControllerGame {

    private Room room;
    private Deck deck;
    private Board boardGame;
    private ArrayList<PersonalTile> personalTiles;
    private ArrayList<ExcommunicationTile> excommunicationTiles;
    private ModelController modelController;
    private LeadersDeck leadersDeck;
    private int numberOfPlayers;
    private int numberOfRound;
    private int numberOfTurn;
    private HashMap<String, Integer> playerChoices;
    private ArrayList<AbstractConnectionPlayer> orderOfPlayers;
    private NetworkChoicesPacketHandler choicesController;
    private int numPersonalTiles;
    private int playerExcommunicatedChoice;

    /**
     * This method creates a new board and modifies it considering the number of players
     * @param players the payers that are on the game
     * @param room the room where the game is located
     * @throws Exception if file where Board configuration is
     */
    public ControllerGame(ArrayList<AbstractConnectionPlayer> players, Room room) throws IOException {
        this.room = room;
        this.orderOfPlayers = players;
        numPersonalTiles = 0;
        JSONLoader.instance();
        boardGame = JSONLoader.boardCreator();
        //boardGame.loadExcommunicationCards(); no need, done in the method boardCreator()
        //excommunicationTiles = JSONLoader.loadExcommunicationTiles();
        personalTiles = JSONLoader.loadPersonalTiles();
        deck = JSONLoader.createNewDeck();
        leadersDeck = JSONLoader.loadLeaders();
        this.numberOfPlayers = players.size();
        boardModifier(numberOfPlayers);
        modelController = new ModelController(players, boardGame);
        numberOfTurn = 0;
        numberOfRound = 1;
        playerChoices = new HashMap<>(10);
        /*on the server we will only need to read choices from hashmap so we set the controller for choices just
        once and the we modify the hashmap on it
         */
        choicesController = new NetworkChoicesPacketHandler(modelController.getBoard().getCouncilAS().getCouncilGiftChoices());
        modelController.setChoicesController(choicesController);
    }

    /**
     * call the method on the controller of the model to start a new game
     * This method also delivers all the necessary informations to the clients
     * and performs the leader draft phase and personal tile choice
     */
    public void startNewGame(){

        //throws the dices to change the value
        modelController.getDices().forEach(Dice::throwDice);

        modelController.setFamilyMemberDices();

        chooseOrderRandomly();

        deliverOrderPlayers();

        //add the coins to the orderOfPlayers based on the order of turn
        modelController.addCoinsStartGame(orderOfPlayers);

        room.receiveStartGameBoard(boardGame);
        deliverDices(modelController.getDices());
        initiatePersonalTileChoice();
    }

    /**
     * this method is used at the start of the game to deliver to the clients the personal tiles to choose
     */
    private void initiatePersonalTileChoice() {

        /*personalTiles.get(0).setPersonalTileEnum(PersonalTileEnum.STANDARD);
        for(int i = 1 ; i < personalTiles.size() ; i++)
            personalTiles.get(i).setPersonalTileEnum(PersonalTileEnum.SPECIAL);*/
        room.deliverPersonalTiles(personalTiles);

    }

    public void choosePersonalTile(PersonalTile personalTile, AbstractConnectionPlayer player){
        modelController.setPersonalTile(personalTile, player.getNickname());
        numPersonalTiles++;
        if(numPersonalTiles == orderOfPlayers.size())
            initiateLeaderChoice();
    }

    /**
     * costructor for controllerGame. This is just a temp constructor to test the class
     * @param numberOfPlayers is the number of players currently playing this game.
     * @throws IOException in case JSON loading fails
     */
    public ControllerGame(int numberOfPlayers) throws IOException {
        JSONLoader.instance();
        personalTiles = JSONLoader.loadPersonalTiles();
        boardGame = JSONLoader.boardCreator();
        deck = JSONLoader.createNewDeck();
        leadersDeck = JSONLoader.loadLeaders();
        boardModifier(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
        numberOfTurn = 0;
        numberOfRound = 1;
    }

    public static void main(String[] args) throws Exception {
        /*GsonBuilder gsonBuilder = new GsonBuilder();
        RuntimeTypeAdapterFactory<ImmediateEffectInterface> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "effectName");
        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        Resource resource = new Resource(ResourceTypeEnum.COIN, 2);
        BuildingCard asd = new BuildingCard();
        //VentureCardMilitaryCost ventureCardMilitaryCost = new VentureCardMilitaryCost(resource, resource);
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(resource);
        asd.setCost(resources);
        System.out.println(gson.toJson(asd));*/
        Debug.instance(3);
        ControllerGame controllerGame =  new ControllerGame(2);

        CliPrinter cli = new CliPrinter();
        //cli.printDeck(controllerGame.getDeck());
        cli.printBoard(controllerGame.getBoardGame());
        controllerGame.testSecondRound(1);
        cli.printBoard(controllerGame.getBoardGame());
        controllerGame.testSecondRound(2);
        cli.printBoard(controllerGame.getBoardGame());
        controllerGame.testSecondRound(2);
        cli.printBoard(controllerGame.getBoardGame());
        controllerGame.testSecondRound(3);
        cli.printBoard(controllerGame.getBoardGame());
        controllerGame.testSecondRound(3);
        cli.printBoard(controllerGame.getBoardGame());
    }

    public void testSecondRound(int period){
        boardGame = deck.fillBoard(boardGame,period);
    }

    private  Deck getDeck()
    {
        return deck;
    }

    /**
     * this method in launch during every end of the player's phase
     * @throws IllegalMoveException
     */
    public void endPhase(AbstractConnectionPlayer player) throws IllegalMoveException{

        controlTurnPlayer(player.getNickname());

        numberOfTurn++;

       //control if the game had ended
        if(numberOfTurn >= numberOfPlayers*4 && numberOfRound == 6){
            ArrayList<PlayerPositionEndGamePacket> playerPositionEndGames = new ArrayList<>(orderOfPlayers.size());
            playerPositionEndGames = modelController.endGame();
            room.deliverEndGame(playerPositionEndGames);
        }

        else{

            //control if the excommunication tiles has to be added
            if(numberOfTurn >= numberOfPlayers*4 && numberOfRound%2 == 0){

                ArrayList<String> nicknamePlayerExcommunicated = new ArrayList<>(modelController.controlExcommunication((numberOfRound/2)+2));
                room.deliverExcommunication(nicknamePlayerExcommunicated, (numberOfRound/2)-1);
                if(nicknamePlayerExcommunicated.size() != orderOfPlayers.size()){
                    playerExcommunicatedChoice = nicknamePlayerExcommunicated.size();
                    //if not all the players had been excommunicated the server has to wait for the choices of the other players
                    return;
                }
            }

            //control if all the player had done all the move
            else if(numberOfTurn >= numberOfPlayers*4) {
                prepareForNewRound();
            }
        }

        deliverStartOfPhase();

    }

    /**
     * this method is called to prepare the board for a new round
     */
    public void prepareForNewRound(){

        Debug.printVerbose("prepare for new round");
        modelController.prepareForNewRound();
        deliverDices(modelController.getDices());
        Debug.printVerbose("Dice delivered");
        numberOfRound++;
        ArrayList<AbstractCard> cardsToPlace = deck.getRandomCards((int)(numberOfRound+1)/2);
        Debug.printVerbose("cards taken");
        room.deliverCardToPlace(cardsToPlace);
        Debug.printVerbose("cards delivered");
        modelController.placeCardOnBoard(cardsToPlace);
        reDoOrderPlayer(modelController.getFamilyMemberCouncil());
        numberOfTurn = 0;

    }

    /**
     * this method is called to deliver the start of the phase to the proper player
     */
    public void deliverStartOfPhase(){

        Debug.printVerbose("deliver start of phase to " +orderOfPlayers.get(numberOfTurn%numberOfPlayers).getNickname());
        //call the method to inform the player that is his turn
        for(Player player1 : orderOfPlayers)
            Debug.printVerbose(player1.getNickname());
        room.playersTurn(orderOfPlayers.get(numberOfTurn%numberOfPlayers));
        for(Player player1 : orderOfPlayers)
            Debug.printVerbose(player1.getNickname());

    }
    /**
     * manage the order of the orderOfPlayers based on the council
     * @param familyMembers the family members placed on the council
     */
    private void reDoOrderPlayer(ArrayList<FamilyMember> familyMembers){

        ArrayList<AbstractConnectionPlayer> newPlayersOrder = new ArrayList<>(orderOfPlayers.size());

        for(FamilyMember i : familyMembers){

            for(AbstractConnectionPlayer player : orderOfPlayers){

                if(i.getPlayer().getNickname().equals(player.getNickname())){

                    newPlayersOrder.add(player);
                    orderOfPlayers.remove(i.getPlayer());

                }
            }
        }

        newPlayersOrder.addAll(orderOfPlayers);
        orderOfPlayers.clear();
        orderOfPlayers = newPlayersOrder;

    }

    public Board getBoardGame() {
        return boardGame;
    }




    /**
     * this method modifies the board given a number of players
     * @param numberOfPlayers the number of players that will play on this game
     */
    private void boardModifier(int numberOfPlayers)
    {
        if(numberOfPlayers == 4)
            return;
        boardThreePlayers();
        if(numberOfPlayers == 3)
            return;
        boardTwoPlayers();
    }


    /**
     * this method modifies the board when there are three players
     */
    private void boardThreePlayers()
    {
        boardGame.getMarket().remove(3);
        boardGame.getMarket().remove(2);
    }

    /**
     * This method modifies the board if there are 2 players.
     */
    private void boardTwoPlayers()
    {
        boardGame.getHarvest().setTwoPlayersOneSpace(true);
        boardGame.getBuild().setTwoPlayersOneSpace(true);
    }



    /**
     * choose the order of the orderOfPlayers at the beginning of the game
     */
    private void chooseOrderRandomly(){

        ArrayList<AbstractConnectionPlayer> playersOrder = new ArrayList<>(orderOfPlayers.size());
        Random random = new Random();

        for(int i=0; i< orderOfPlayers.size();){

            int valueIndex = random.nextInt(orderOfPlayers.size());
            //add the player of the index
            playersOrder.add(orderOfPlayers.get(valueIndex));
            //remove the player of the index
            orderOfPlayers.remove(valueIndex);

        }
        orderOfPlayers.addAll(playersOrder);

        room.updateOrderPlayer(orderOfPlayers);

    }

    /**
     * call the method on the controller of the model to place on the tower
     * @param familyMember the familymember the player places
     * @param towerIndex the number of the tower where the family member is placed
     * @param floorIndex the number of floor on the tower the family member is placed
     */
    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws IllegalMoveException{

        controlTurnPlayer(familyMember.getPlayer().getNickname());

        choicesController.setChoicesMap(playerChoices);
        modelController.placeOnTower(familyMember, towerIndex, floorIndex);

    }

    /**
     * call the method on the controller of the model to harvest
     * @param familyMember the family member the player places
     * @param servant the number of servant you add on the family member to increase the value
     * @param playerChoices the choices done by the player ont the resource to get when harvesting
     */
    public void harvest(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws IllegalMoveException{

        choicesController.setChoicesMap(playerChoices);
        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.setChoicesController(choicesController);
        modelController.harvest(familyMember, servant);

    }

    /**
     * call the method on the controller of the model to build
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     */
    public void build(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws IllegalMoveException{

        choicesController.setChoicesMap(playerChoices);
        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.build(familyMember, servant);

    }

    /**
     * this method is called by the room to deliver the move on council from a player
     * @param familyMember the family member the player wants to place on the council
     * @param playerChoices the choices taken from the player when an effect have different choices
     */
    public void placeOnCouncil(FamilyMember familyMember, HashMap<String, Integer> playerChoices) throws IllegalMoveException{

        controlTurnPlayer(familyMember.getPlayer().getNickname());
        choicesController.setChoicesMap(playerChoices);
        modelController.setChoicesController(choicesController);
        modelController.placeOnCouncil(familyMember);
    }

    /**
     * call the method on the controller of the model to place on the market
     * @param familyMember the familymember the player places
     * @param marketSpaceIndex the number of servant you add on the family member to increase the value
     */
    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex, HashMap<String, Integer> playerChoices) throws IllegalMoveException{

        choicesController.setChoicesMap(playerChoices);
        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.placeOnMarket(familyMember, marketSpaceIndex);

    }

    /**
     * call the method on the controller of the model to discard a leader card
     * @param nickname the nickname of the player who has the card
     * @param nameLeader the name of the leader card
     */
    public void discardLeaderCard(String nickname, String nameLeader, HashMap<String, Integer> resourceGet) throws IllegalMoveException{

        controlTurnPlayer(nickname);
        choicesController.setChoicesMap(resourceGet);
        modelController.setChoicesController(choicesController);
        modelController.discardLeaderCard(nickname, nameLeader);

    }

    /**
     * call the method on the controller of the model to activate a leader card
     * @param nameLeader the name of the leader card
     * @param choicesOnCurrentActionString
     * @param player the player who has the card
     * @param choicesOnCurrentAction
     */
    public void playLeaderCard(String nameLeader, HashMap<String, String> choicesOnCurrentActionString, Player player, HashMap<String, Integer> choicesOnCurrentAction) throws IllegalMoveException{

        controlTurnPlayer(player.getNickname());
        choicesController.setChoicesMapString(choicesOnCurrentActionString);
        choicesController.setChoicesMap(choicesOnCurrentAction);
        modelController.setChoicesController(choicesController);
        modelController.playLeaderCard(nameLeader, player);

    }

    /**
     * control if is the turn of the player that had delivered a move
     */
    private void controlTurnPlayer(String playerName) throws IllegalMoveException{

        if(!playerName.equals(orderOfPlayers.get(numberOfTurn%numberOfPlayers).getNickname()))
            throw new IllegalMoveException();

    }

    /**
     * deliver the dices loaded to the room
     */
    private void deliverDices(ArrayList<Dice> dices){

        room.deliverDices(dices);

    }

    /**
     * this method is used to deliver to the room the nickname in order of turn, then the room will deliver this nickname to the client
     */
    private void deliverOrderPlayers(){

        ArrayList<String> orderPlayers = new ArrayList<>(numberOfPlayers);
        for(AbstractConnectionPlayer player : orderOfPlayers)
            orderPlayers.add(player.getNickname());
        room.deliverOrderPlayers(orderPlayers);
    }

    /**
     * this method is used to deliver the leader cards to the players
     */
    private void initiateLeaderChoice(){

        ArrayList<LeaderCard> cardsInTheDeck = leadersDeck.getLeaders();
        ArrayList<LeaderCard> leaderCards = new ArrayList<>(4*numberOfPlayers);
        Random random = new Random();
        for(int i = 0 ; i< 4*numberOfPlayers ; i++){
            int index = random.nextInt(cardsInTheDeck.size());
            leaderCards.add(cardsInTheDeck.get(index));
            cardsInTheDeck.remove(index);
        }

        room.initiateLeaderChoice(leaderCards);
    }

    /**
     * this method is called by the room to deliver the leader card the player had chosen
     * @param leaderCard the leade card chose
     * @param player the player that had done the choice
     */
    public void choiceLeaderCard(LeaderCard leaderCard, AbstractConnectionPlayer player) {

        modelController.addLeaderCardToPlayer(leaderCard, player);

    }

    /**
     * this method is called by the room when all the leader cards are chosen by the player
     * It delivers also the cards to be placed on the board
     */
    public void choseAllTheLeadersCards() {
        ArrayList<AbstractCard> cardsToPlace = deck.getRandomCards(numberOfRound);
        room.deliverCardToPlace(cardsToPlace);
        modelController.placeCardOnBoard(cardsToPlace);
        room.playersTurn(orderOfPlayers.get(0));
    }

    /**
     * this method is called by the room to inform the controller that a player had left the game
     * @param nickname the nickname of the player that is no more connected
     */
    public void disconnectedPlayer(String nickname) {
        int position = 0;
        for(AbstractConnectionPlayer player : orderOfPlayers) {
            if(player.getNickname().equals(nickname)){

                modelController.removePlayer(player);
                correctionRound(position, player);
                orderOfPlayers.remove(player);
                numberOfPlayers = orderOfPlayers.size();

            }
            position++;

        }
    }

    /**
     * this method is used to align the value of the phase with the number of player connected
     * this method is called when a player left the game
     * @param position the position of the player on the the order of turn
     */
    private void correctionRound(int position, AbstractConnectionPlayer player) {

        //this is the position on the order of turn of the last player that had ended the phase
        int actualPosition = numberOfTurn%numberOfPlayers;
        //this is used to subtract every turn player by the player
        numberOfTurn -= numberOfTurn/numberOfPlayers;
        if(actualPosition >= position)
            //this means that in this round the disconnected player had already done his move
            numberOfTurn--;
        else if((actualPosition+1)%numberOfPlayers == position){
            //this means that now is the turn of the disconnected player
            //the 'if' is used to control if the player that had passed the last time is the player before the disconnected player
            try{
                endPhase(player);
            }
            catch (IllegalMoveException e){
                Debug.printError(e);
            }

        }
    }

    /**
     * this method is called by room to deliver the leader card activated by a player
     * @param nameCard the name of the leader card activated
     * @param resourceGet the resources gotten activating a leader card
     * @param player the name of the player that had activated the leader card
     */
    public void activateLeaderCard(String nameCard, HashMap<String, Integer> resourceGet, AbstractConnectionPlayer player) throws IllegalMoveException {

        controlTurnPlayer(player.getNickname());
        choicesController.setChoicesMap(resourceGet);
        player.activateLeaderCardAbility(player.getLeaderCardsPlayedButNotActivated(nameCard), choicesController);
    }

    public int getNumberOfRound() {
        return numberOfRound;
    }

    /**
     * this method is called by room to deliver the response of the players to the excommunication coice
     * @param response the response, "yes" if the player wants to avoid he excommunication
     *                               "no" otherwise
     * @param nickname the nickname of the player that had done the choice
     * @param numTile the number of tile to take if the excommunication happens
     */
    public void receiveExcommunicationChoice(String response, String nickname, int numTile) {

        if(response.equals("yes")){
            modelController.avoidExcommunicationPlayer(nickname);
        }
        else
            modelController.excommunicatePlayer(nickname, numTile);
        playerExcommunicatedChoice++;
        if(playerExcommunicatedChoice == orderOfPlayers.size()){
            prepareForNewRound();
            deliverStartOfPhase();
        }

    }
}

