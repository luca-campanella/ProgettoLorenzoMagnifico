package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Exception class to handle communication errors between server and client
 */
public class NetworkException extends IOException {

    public NetworkException(Throwable e)
    {
        super(e);
    }
    public NetworkException(String cause)
    {
        super(cause);
    }
}
