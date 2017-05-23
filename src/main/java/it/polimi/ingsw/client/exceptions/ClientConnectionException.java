package it.polimi.ingsw.client.exceptions;

import java.io.IOException;

/**
 * Exception used when the client can't connect to the controller
 * @extends IOException
 */
public class ClientConnectionException extends IOException {

    /**
     * Constructor, just calls the super constructor of IOException
     * @param e exceptio
     */
    public ClientConnectionException(Throwable e)
    {
        super(e);
    }

    /**
     * Constructor, just calls the super constructor of IOException
     * @param msg cause
     * @param e exception
     */
    public ClientConnectionException(String msg, Throwable e)
    {
        super(msg, e);
    }
}
