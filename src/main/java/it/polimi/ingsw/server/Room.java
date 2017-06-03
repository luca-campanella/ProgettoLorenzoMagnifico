package it.polimi.ingsw.server;

import it.polimi.ingsw.client.exceptions.IllegalMoveException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the class that handles a room and offers a layer between the network part of the server and the actual game
 */
public class Room {

    /**
     * Array of order of the players in the room, its dimension is set in the constructor
     */
    ArrayList<AbstractConnectionPlayer> players;

    ControllerGame controllerGame;

    /**
     * timeout that starts when the second player joins the room. When time is up game starts. Set by the constructor
     */
    private int timeoutInSec;

    private int maxNOfPlayers;
    private int currNOfPlayers;
    private boolean isGameStarted;
    /**
     * Constructor
     * @param maxNOfPlayers max number of players for this room
     * @param timeoutInSec timeout that starts when the second player joins the room. When time is up game starts
     */
    public Room(int maxNOfPlayers, int timeoutInSec)
    {
        this.timeoutInSec = timeoutInSec;
        this.maxNOfPlayers = maxNOfPlayers;
        currNOfPlayers = 0;
        isGameStarted = false;
        players = new ArrayList<AbstractConnectionPlayer>(maxNOfPlayers);
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public boolean canJoin(AbstractConnectionPlayer player) {
        for(AbstractConnectionPlayer i : players) {
            if(i.getNickname().equals(player.getNickname()))
                return false;
        }
        return true;
    }


    /**
     * adds new player to the room, it also binds the player with the instance of the room
     * @param player the istance of the player to add
     */
    public void addNewPlayer(AbstractConnectionPlayer player)
    {
        players.add(player);
        player.setRoom(this);
        currNOfPlayers++;
        Debug.printDebug("*Room*: added player " + player.getNickname());
        if(currNOfPlayers == maxNOfPlayers) //ModelController should start
        {
            Debug.printVerbose("Room capacity reached, starting new game");
            startGame();
            Debug.printVerbose("Room capacity reached, returned from start function");
        }
        else if(currNOfPlayers == 2) //TODO good idea to load this from file
        {
            Debug.printVerbose("2 players reached ");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Debug.printVerbose("Timeout reached, starting new game");
                    startGame();
                    Debug.printVerbose("Timeout reached, returned from start function");
                }
            }, timeoutInSec);
        }
    }

    /**
     * when the time out is ended or the room is full the game start to prepare all the object needed on the game
     */
    private void startGame()
    {
        Debug.printVerbose("Game on room started");
            isGameStarted = true;
            try{
                controllerGame = new ControllerGame(players, this);
                controllerGame.startNewGame();
            }
            catch (Exception e) {
                Debug.printError("Connection Error", e);
            }
    }

    /**
     * reload the order of player when it changes
     * @param orderPlayers the order of players
     */
    public void updateOrderPlayer(ArrayList<AbstractConnectionPlayer> orderPlayers){

        this.players = orderPlayers;

    }

    /**
     * This method is called by a the (@link AbstractConnectionPlayer) that wants to send a message (Direction: AbstractConnectionPlayer -> Room)
     * @param player the sender
     * @param msg
     */
    public void floodChatMsg(AbstractConnectionPlayer player, String msg) {
        for(AbstractConnectionPlayer i : players) {
            if(player != i) {//the message should not be sent to the sender
                try {
                    i.receiveChatMsg(player.getNickname(), msg);
                } catch (NetworkException e) { //not a big problem if a chat message is not sent
                    Debug.printError("Unable to sent chat message to " + i.getNickname(), e);
                }
            }
        }
    }

    /**
     * call the method on controller game to place a family member on the tower
     * @throws IllegalMoveException if the player doesn't have the correct resources to do the action
     */
    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws IllegalMoveException{

        controllerGame.placeOnTower(familyMember, towerIndex, floorIndex, playerChoices);
        floodPlaceOnTower(familyMember, towerIndex, floorIndex, playerChoices);

    }

    /**
     * the method called by the client to do place a family member on the market
     * @throws IllegalMoveException if the player doesn't have the correct resources to do the action
     */
    public void placeOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws IllegalMoveException{

        controllerGame.placeOnMarket(familyMember, marketIndex, playerChoices);
        floodPlaceOnMarket(familyMember, marketIndex, playerChoices);

    }

    /**
     * call the method on controller game to build
     * @throws IllegalMoveException if the player doesn't have the correct resources to do the action
     */
    public void build(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws  IllegalMoveException{

        controllerGame.build(familyMember, servant, playerChoices);
        floodBuild(familyMember, servant, playerChoices);

    }

    /**
     * call the method on controller game to harvest
     * @throws IllegalMoveException if the player doesn't have the correct resources to do the action
     */
    public void harvest(FamilyMember familyMember, int servant) throws  IllegalMoveException{

        controllerGame.harvest(familyMember, servant);
        floodHarvest(familyMember, servant);

    }

    /**
     * launch the move of a player to the others player
     */
    private void floodPlaceOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices){

        for(AbstractConnectionPlayer player : players) {
            if(!player.getNickname().equals(familyMember.getPlayer().getNickname())) {
                try {
                    player.receivePlaceOnTower(familyMember, towerIndex, floorIndex, playerChoices);
                } catch (NetworkException e) { //not a big problem if a chat message is not sent
                    Debug.printError("Unable to sent chat message to " + player.getNickname(), e);
                }
            }
        }
    }

    /**
     * launch the move of a player to the others player
     */
    private void floodBuild(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices){

        for(AbstractConnectionPlayer player : players) {
            if(!player.getNickname().equals(familyMember.getPlayer().getNickname())) {
                try {
                    player.receiveBuild(familyMember, servant, playerChoices);
                } catch (NetworkException e) { //not a big problem if a chat message is not sent
                    Debug.printError("Unable to sent chat message to " + player.getNickname(), e);
                }
            }
        }
    }

    /**
     * launch the move of a player to the others player
     */
    private void floodHarvest(FamilyMember familyMember, int servant){

        for(AbstractConnectionPlayer player : players) {
            if(!player.getNickname().equals(familyMember.getPlayer().getNickname())) {
                try {
                    player.receiveHarvest(familyMember, servant);
                } catch (NetworkException e) { //not a big problem if a chat message is not sent
                    Debug.printError("Unable to sent chat message to " + player.getNickname(), e);
                }
            }
        }
    }

    /**
     * launch the move of a player to the others player
     */
    private void floodPlaceOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices){

        for(AbstractConnectionPlayer player : players) {
            if(!player.getNickname().equals(familyMember.getPlayer().getNickname())) {
                try {
                    player.receivePlaceOnMarket(familyMember, marketIndex, playerChoices);
                } catch (NetworkException e) { //not a big problem if a chat message is not sent
                    Debug.printError("Unable to sent chat message to " + player.getNickname(), e);
                }
            }
        }
    }
}