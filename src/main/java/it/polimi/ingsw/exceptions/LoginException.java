package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Exception to handle problem with the login of a player
 * @extends IOException
 */
public class LoginException extends IOException {

    private LoginErrorEnum errorType;

    public LoginException(LoginErrorEnum errorType) {
        this.errorType = errorType;
    }

    public LoginException(LoginErrorEnum errorType, String msg) {
        super(msg);
        this.errorType = errorType;
    }

    public LoginErrorEnum getErrorType() {
        return errorType;
    }
}
