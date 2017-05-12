package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Exception to handle problem with the login of a player
 * @extends IOException
 */
public class LoginException extends IOException {
    public enum Error { NOT_EXISTING_USERNAME, WRONG_PASSWORD, ALREADY_LOGGED};

    private Error errorType;

    public LoginException(Error errorType) {
        this.errorType = errorType;
    }

    public LoginException(Error errorType, String msg) {
        super(msg);
        this.errorType = errorType;
    }
}
