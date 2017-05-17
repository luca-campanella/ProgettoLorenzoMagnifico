package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.packet.ErrorType;

import java.io.IOException;

/**
 * Exception to handle problem with the login of a player
 * @extends IOException
 */
public class LoginException extends IOException {

    /**
     * The same player can play multiple games, but not in the same room
     */

    private ErrorType errorType;

    public LoginException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public LoginException(ErrorType errorType, String msg) {
        super(msg);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
