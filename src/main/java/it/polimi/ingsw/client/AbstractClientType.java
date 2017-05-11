package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.ClientConnectionException;

/**
 * This is the abstract class that represent the network part of the client, can be extended by RMIClient or SocketClient, depending on which connection the user chooses
 */
public abstract class AbstractClientType  {

    /**
     * the instance of the controller to call the callback functions and communicate from server to client
     */
    protected ClientMain controllerMain;

    /**
     * The address of the server to connect to
     */
    protected String serverAddress;

    /**
     * the port of the server to connect to
     */
    protected int port;


    public abstract void connect() throws ClientConnectionException;

    public AbstractClientType(ClientMain controllerMain, String serverAddress, int port) {
        this.controllerMain = controllerMain;
        this.serverAddress = serverAddress;
        this.port = port;
    }
}
