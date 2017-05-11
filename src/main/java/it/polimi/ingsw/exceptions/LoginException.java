package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Exception to handle problem with the login of a player
 */
public class LoginException extends IOException {
    public static final int NOT_EXISTING_USERNAME = 1;
    public static final int WRONG_PASSWORD = 2;
    public static final int ALREADY_LOGGED = 3;

    private int errorCode;

    public LoginException(int errorCode) {
        this.errorCode = errorCode;
    }

    public LoginException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
