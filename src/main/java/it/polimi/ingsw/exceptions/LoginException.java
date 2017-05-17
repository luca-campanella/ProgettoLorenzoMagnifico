package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Exception to handle problem with the login of a player
 * @extends IOException
 */
public class LoginException extends IOException {

    /**
     * The same player can play multiple games, but not in the same room
     */

    /**
     * The enum to signal what caused the login error
     */
    public enum LoginErrorType {NOT_EXISTING_USERNAME, WRONG_PASSWORD, ALREADY_LOGGED_TO_ROOM};

    private LoginErrorType errorType;

    public LoginException(LoginErrorType errorType) {
        this.errorType = errorType;
    }

    public LoginException(LoginErrorType errorType, String msg) {
        super(msg);
        this.errorType = errorType;
    }

    public LoginErrorType getErrorType() {
        return errorType;
    }
}
