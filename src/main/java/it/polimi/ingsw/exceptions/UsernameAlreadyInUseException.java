package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * This exception type is used when a client tries to register a user with a username / nickname already in use
 */
public class UsernameAlreadyInUseException extends IOException {
    public UsernameAlreadyInUseException(String msg) {
        super(msg);
    }
}
