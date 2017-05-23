package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.network.AbstractConnectionPlayer;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.model.controller.GameController;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;

/**
 * This is the class that handles a room and offers a layer between the network part of the controller and the actual game
 */
public class Room {

    /**
     * Array of players in the room, its dimension is set in the constructor
     */
    ArrayList<AbstractConnectionPlayer> players;

    GameController gameController;

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
        if(currNOfPlayers == maxNOfPlayers) //GameController should start
        {
            isGameStarted = true;
            gameController = new GameController(currNOfPlayers, this);
        }
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
}
