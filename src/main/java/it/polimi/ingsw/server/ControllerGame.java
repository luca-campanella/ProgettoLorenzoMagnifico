package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.controller.ModelController;
import it.polimi.ingsw.model.effects.immediateEffects.GiveCouncilGiftEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.testingGSON.boardLoader.BoardCreator;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by higla on 29/05/2017.
 */
public class ControllerGame {
    private ControllerGame game;
    private Room room;
    private Board boardGame;
    private ModelController modelController;
    private int numberOfPlayers;
    private int numberOfRound;
    private int numberOfTurn;

    public static void main(String[] args) throws Exception {
        ControllerGame controllerGame =  new ControllerGame(2);

        CliPrinter cli = new CliPrinter();
        //todo: load cards, implement these on board;
        cli.printBoard(controllerGame.getBoardGame());
    }

    public void endTurn(){

        numberOfTurn++;

        if(numberOfTurn >= numberOfPlayers*4 && numberOfRound == 3)
            modelController.endGame();

        if(numberOfTurn >= numberOfPlayers*4){
            modelController.prepareForNewRound();
            numberOfTurn = 0;
            numberOfRound++;
        }

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
    public ControllerGame(ArrayList<Player> players, Room room) throws Exception {
        JSONLoader jsonLoader = new JSONLoader();
        boardGame = jsonLoader.boardCreator();
        boardGame.setDeck(jsonLoader.createNewDeck());
        numberOfPlayers = players.size();
        boardModifier(numberOfPlayers);
        this.room = room;
        modelController = new ModelController(players, this, boardGame);
        numberOfTurn = 0;
        numberOfRound = 1;
    }

    /**
     * costructor for controllerGame
     * @param numberOfPlayers
     * @throws Exception
     */
    public ControllerGame(int numberOfPlayers) throws Exception {
        JSONLoader jsonLoader = new JSONLoader();
        boardGame = jsonLoader.boardCreator();
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

    }

    /**
     * call the method on the controller of the model to place on the tower
     * @param familyMember the familymember the player places
     * @param towerIndex the number of the tower where the family member is placed
     * @param floorIndex the number of floor on the tower the family member is placed
     */
    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex){

        modelController.placeOnTower(familyMember, towerIndex, floorIndex);

    }

    /**
     * call the method on the controller of the model to harvest
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     */
    public void harvest(FamilyMember familyMember, int servant){

        modelController.harvest(familyMember, servant);

    }

    /**
     * call the method on the controller of the model to build
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     */
    public void build(FamilyMember familyMember, int servant){

        modelController.build(familyMember, servant);

    }

    /**
     * call the method on the controller of the model to place on the market
     * @param familyMember the familymember the player places
     * @param marketSpaceIndex the number of servant you add on the family member to increase the value
     */
    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex){

        modelController.placeOnMarket(familyMember, marketSpaceIndex);

    }

    /**
     * call the method on the controller of the model to discard a leader card
     * @param player the player who has the card
     * @param nameLeader the name of the leader card
     */
    public void discardLeaderCard(Player player, String nameLeader){

        modelController.discardLeaderCard(player, nameLeader);

    }

    /**
     * call the method on the controller of the model to activate a leader card
     * @param player the player who has the card
     * @param nameLeader the name of the leader card
     */
    public void playLeaderCard(Player player, String nameLeader){

        modelController.activateLeaderCard(player, nameLeader);

    }


}

