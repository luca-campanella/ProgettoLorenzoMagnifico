package it.polimi.ingsw.client.exceptions;

import java.io.IOException;

/**
 * Exception class to handle communication errors between controller and client
 * @extends IOException
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
    public NetworkException(String msg, Throwable e) {
        super(msg, e);
    }
}
