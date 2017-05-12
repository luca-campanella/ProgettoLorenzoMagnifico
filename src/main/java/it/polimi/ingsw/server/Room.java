package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.FullRoomException;
import it.polimi.ingsw.exceptions.GameAlreadyStartedRoomException;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;

/**
 * This is the class that handles a room and offers a layer between the network part of the server and the actual game
 */
public class Room {

    /**
     * Array of players in the room, its dimension is set in the constructor
     */
    ArrayList<AbstractConnectionPlayer> players;

    //Game game;

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

    /**
     * adds new player to the room
     * @param player the istance of the player to add
     * @throws FullRoomException if the room is full, but the game is not started yet, this should never happen
     * @throws GameAlreadyStartedRoomException if the game has already started and thus no player can join
     */
    public void addNewPlayer(AbstractConnectionPlayer player) throws FullRoomException, GameAlreadyStartedRoomException
    {
        //TODO are we sure we need the FullRoomException
        if(isGameStarted)
            throw new GameAlreadyStartedRoomException("The game on this room is already started, can't add a player");
        if(currNOfPlayers >= maxNOfPlayers)
            throw new FullRoomException("Room already full, but not yet started (?)");
        players.add(player);
        currNOfPlayers++;
        Debug.printDebug("*Room*: added player " + player.getNickname());
    }
}
