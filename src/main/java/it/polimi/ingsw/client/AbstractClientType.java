package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.exceptions.NetworkException;

import javax.security.auth.login.LoginException;
import java.rmi.RemoteException;

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

    /**
     * this method is used when an user already exists and decides to login with his username and password
     * @param nickname
     * @param password
     * @throws NetworkException if something goes wrong during the connection
     * @throws LoginException if username doesn't exist or if password is wrong
     */
    public abstract void loginPlayer(String nickname, String password) throws NetworkException, LoginException;

    /**
     * this method is used when the user has never played and wants to create an account
     * @param nickname to register in the server DB
     * @param password to register in the server DB
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void registerPlayer(String nickname, String password) throws NetworkException;
}
