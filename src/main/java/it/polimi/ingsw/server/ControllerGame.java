package it.polimi.ingsw.server;

import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.client.controller.ControllerModelInterface;
import it.polimi.ingsw.client.exceptions.IllegalMoveException;
import it.polimi.ingsw.client.exceptions.MoveErrorEnum;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.controller.ModelController;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * the controller of the game on the server
 */
public class ControllerGame  implements ControllerModelInterface {
    private ControllerGame game;
    private Room room;
    private Deck deck;
    private Board boardGame;
    private ModelController modelController;
    private int numberOfPlayers;
    private int numberOfRound;
    private int numberOfTurn;
    private HashMap<String, Integer> playerChoices;
    private ArrayList<AbstractConnectionPlayer> orderOfPlayers;

    public static void main(String[] args) throws Exception {
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

    public void endPhase(AbstractConnectionPlayer player) throws IllegalMoveException{

        controlTurnPlayer(player.getNickname());

        numberOfTurn++;

        if(numberOfTurn >= numberOfPlayers*4 && numberOfRound == 3)
            modelController.endGame();

        if(numberOfTurn >= numberOfPlayers*4){
            modelController.prepareForNewRound();
            reDoOrderPlayer(modelController.getFamilyMemberCouncil());
            numberOfTurn = 0;
            numberOfRound++;

        }

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
     * This method creates a new board and modifies it considering the number of players
     * @param players the payers that are on the game
     * @param room the room where the game is located
     * @throws Exception if file where Board configuration is
     */
    public ControllerGame(ArrayList<AbstractConnectionPlayer> players, Room room) throws Exception {
        JSONLoader jsonLoader = new JSONLoader();
        boardGame = jsonLoader.boardCreator();
        deck = jsonLoader.createNewDeck();
        numberOfPlayers = players.size();
        boardModifier(numberOfPlayers);
        this.room = room;
        this.orderOfPlayers = players;
        modelController = new ModelController(players, boardGame);
        numberOfTurn = 0;
        numberOfRound = 1;
        playerChoices = new HashMap<>(10);
    }

    /**
     * costructor for controllerGame. This is just a temp constructor to test the class
     * @param numberOfPlayers
     * @throws Exception
     */
    public ControllerGame(int numberOfPlayers) throws Exception {
        JSONLoader jsonLoader = new JSONLoader();
        ArrayList<PersonalTile> personalTiles = jsonLoader.loadTiles();
        boardGame = jsonLoader.boardCreator();
        deck = jsonLoader.createNewDeck();
        int period = 1;
        boardGame = deck.fillBoard(boardGame, period);
        boardModifier(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;

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
     * call the method on the controller of the model to start a new game
     */
    public void startNewGame(){

        modelController.startNewGame();

        chooseOrderRandomly();

        //add the coins to the orderOfPlayers based on the order of turn
        modelController.addCoinsStartGame(orderOfPlayers);

    }

    /**
     * choose the order of the orderOfPlayers at the beginning of the game
     */
    private void chooseOrderRandomly(){

        ArrayList<AbstractConnectionPlayer> playersOrder = new ArrayList<>(orderOfPlayers.size());
        Random random = new Random();
        int valueIndex;
        for(int i=0; i< orderOfPlayers.size();){

            valueIndex = random.nextInt(orderOfPlayers.size());
            //add the player of the index
            playersOrder.add(orderOfPlayers.get(random.nextInt(valueIndex)));
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
        modelController.placeOnTower(familyMember, towerIndex, floorIndex);

    }

    /**
     * call the method on the controller of the model to harvest
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     */
    public void harvest(FamilyMember familyMember, int servant)  throws IllegalMoveException{

        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.harvest(familyMember, servant);


    }

    /**
     * call the method on the controller of the model to build
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     */
    public void build(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices)  throws IllegalMoveException{

        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.build(familyMember, servant);

    }

    /**
     * call the method on the controller of the model to place on the market
     * @param familyMember the familymember the player places
     * @param marketSpaceIndex the number of servant you add on the family member to increase the value
     */
    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex, HashMap<String, Integer> playerChoices)  throws IllegalMoveException{

        controlTurnPlayer(familyMember.getPlayer().getNickname());
        modelController.placeOnMarket(familyMember, marketSpaceIndex);

    }

    /**
     * call the method on the controller of the model to discard a leader card
     * @param player the player who has the card
     * @param nameLeader the name of the leader card
     */
    public void discardLeaderCard(Player player, String nameLeader)  throws IllegalMoveException{

        controlTurnPlayer(player.getNickname());
        modelController.discardLeaderCard(player, nameLeader);

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

    public int choose(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer){

        return playerChoices.get(nameCard);

    }

    private void controlTurnPlayer(String playerName) throws IllegalMoveException{

        if(!playerName.equals(orderOfPlayers.get(numberOfTurn%numberOfPlayers).getNickname()))
            throw new IllegalMoveException(MoveErrorEnum.NOT_PLAYER_TURN);

    }

}

