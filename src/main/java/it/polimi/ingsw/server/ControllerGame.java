package it.polimi.ingsw.server;

import it.polimi.ingsw.choices.NetworkChoicesPacketHandler;
import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.exceptions.IllegalMoveException;
import it.polimi.ingsw.client.exceptions.MoveErrorEnum;
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

       //control if the game gad ended
        if(numberOfTurn >= numberOfPlayers*4 && numberOfRound == 3)
            modelController.endGame();

        //control if all the player had done all the move
        if(numberOfTurn >= numberOfPlayers*4){
            modelController.prepareForNewRound();
            deliverDices(modelController.getDices());
            numberOfRound++;
            ArrayList<AbstractCard> cardsToPlace = deck.getRandomCards(numberOfRound);
            room.deliverCardToPlace(cardsToPlace);
            modelController.placeCardOnBoard(cardsToPlace);
            reDoOrderPlayer(modelController.getFamilyMemberCouncil());
            numberOfTurn = 0;


        }

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
     * @throws IllegalMoveException if the move is not correct
     */
    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws IllegalMoveException {

        controlTurnPlayer(familyMember.getPlayer().getNickname());

        choicesController.setChoicesMap(playerChoices);
        modelController.placeOnTower(familyMember, towerIndex, floorIndex);

    }

    /**
     * call the method on the controller of the model to harvest
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     * @param playerChoices
     */
    public void harvest(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices){

        choicesController.setChoicesMap(playerChoices);
        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.harvest(familyMember, servant);


    }

    /**
     * call the method on the controller of the model to build
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     */
    public void build(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices){

        choicesController.setChoicesMap(playerChoices);
        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.build(familyMember, servant);

    }

    /**
     * this method is called by the room to deliver the move on council from a player
     * @param familyMember the family member the player wants to place on the council
     * @param playerChoices the choices taken from the player when an effect have different choices
     */
    public void placeOnCouncil(FamilyMember familyMember, HashMap<String, Integer> playerChoices){

        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.placeOnCouncil(familyMember);
    }

    /**
     * call the method on the controller of the model to place on the market
     * @param familyMember the familymember the player places
     * @param marketSpaceIndex the number of servant you add on the family member to increase the value
     */
    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex, HashMap<String, Integer> playerChoices)  throws IllegalMoveException{

        choicesController.setChoicesMap(playerChoices);
        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.placeOnMarket(familyMember, marketSpaceIndex);

    }

    /**
     * call the method on the controller of the model to discard a leader card
     * @param nickname the nickname of the player who has the card
     * @param nameLeader the name of the leader card
     */
    public void discardLeaderCard(String nickname, String nameLeader, HashMap<String, Integer> resourceGet){

        controlTurnPlayer(nickname);
        choicesController.setChoicesMap(resourceGet);
        modelController.discardLeaderCard(nickname, nameLeader,choicesController);

    }

    /**
     * call the method on the controller of the model to activate a leader card
     * @param player the player who has the card
     * @param nameLeader the name of the leader card
     */
    public void playLeaderCard(Player player, String nameLeader)  throws IllegalMoveException{

        controlTurnPlayer(player.getNickname());
        modelController.activateLeaderCard(player, nameLeader);

    }

    /**
     * control if is the turn of the player that had delivered a move
     */
    private void controlTurnPlayer(String playerName){

        if(!playerName.equals(orderOfPlayers.get(numberOfTurn%numberOfPlayers).getNickname()))
            room.deliverError(playerName);

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

}

