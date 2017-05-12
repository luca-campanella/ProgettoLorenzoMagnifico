package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Used to signal that the room is full
 */
public class FullRoomException extends IOException {

    /**
     * just calls the super constructor
     * @param message
     */
    public FullRoomException(String message)
    {
        super(message);
    }
}
