package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Created by campus on 11/05/2017.
 */
public class LoginException extends IOException {
    public static final int NOT_EXISTING_USERNAME = 1;
    public static final int WRONG_PASSWORD = 2;
    public static final int ALREADY_LOGGED = 3;

    private int errorCode;

    public LoginExcpetion
}
