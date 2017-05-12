package it.polimi.ingsw.client;


import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.net.Socket;

/**
 * SocketClient is the class of client that communicates to the server using the socket
 */
public class SocketClient extends AbstractClientType {
    /**
     *socket connected to the server
     */
    private Socket socketClient;
    /**
     * Information getted from the server
     */
    private Object inputInformation;
    /**
     * Informations delivered to the server
     */
    private Object outputInformation;
    /**
     *Initialization of the attributes on the superclass
     */
    public SocketClient(ClientMain controllerMain, String serverAddress, int port){
        super(controllerMain, serverAddress, port);
    }

    /**
     * this method is used when an user already exists and decides to login with his username and password
     *
     * @param nickname
     * @param password
     * @throws NetworkException if something goes wrong during the connection
     * @throws LoginException   if username doesn't exist or if password is wrong
     */
    @Override
    public void loginPlayer(String nickname, String password) throws NetworkException, LoginException {
        //TODO implement the method loginPlayer in socket
    }

    /**
     * this method is used when the user has never played and wants to create an account
     *
     * @param nickname to register in the server DB
     * @param password to register in the server DB
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void registerPlayer(String nickname, String password) throws NetworkException {
        //TODO implement the method loginPlayer in socket
    }

    /**
     * override of the superMethod
     * @throws ClientConnectionException if the connection had failed
     */
    public void connect() throws ClientConnectionException {
        try{
            socketClient = new Socket(getServerAddress(),getPort());
        }
        catch(IOException e){
            Debug.printError("Cannot connect Socket client",e);
            throw new ClientConnectionException(e);
        }

    }



}
