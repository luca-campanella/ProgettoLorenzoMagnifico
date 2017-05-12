package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * Exception used when the client can't connect to the server
 * @extends IOException
 */
public class ClientConnectionException extends IOException {

    /**
     * Constructor, just calls the super constructor of IOException
     * @param e
     */
    public ClientConnectionException(Throwable e)
    {
        super(e);
    }
}
