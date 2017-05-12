package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Used to signal that the game in the room is already started and thus you can't add a player
 * @extends IOException
 */
public class GameAlreadyStartedRoomException extends IOException {

    /**
     * just calls the super constructor
     * @param message
     */
    public GameAlreadyStartedRoomException(String message)
    {
        super(message);
    }
}
