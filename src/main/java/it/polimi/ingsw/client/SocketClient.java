package it.polimi.ingsw.client;


import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.utils.Debug;

import java.net.Socket;
import java.io.*;
import java.util.*;

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
     * override of the superMethod
     * @throws ClientConnectionException if the connection had failed
     */
    public void connect() throws ClientConnectionException {
        try{
            socketClient = new Socket(serverAddress,port);
        }
        catch(IOException e){
            Debug.printError("Cannot connect Socket client",e);
            throw new ClientConnectionException(e);
        }
        System.out.println("Connection established");

    }



}
