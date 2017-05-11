package it.polimi.ingsw.client;

/**
 * This is the abstract class that represent the network part of the client, can be extended by RMIClient or SocketClient, depending on which connection the user chooses
 */
public class AbstractClientType  {

    /**
     * the instance of the controller to call the callback functions and communicate from server to client
     */
    private ClientMain controllerMain;

    /**
     * The address of the server to connect to
     */
    private String serverAddress;

    /**
     * the port of the server to connect to
     */
    private int port;




}
