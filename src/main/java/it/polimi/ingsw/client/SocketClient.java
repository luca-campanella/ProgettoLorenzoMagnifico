package client

import java.net.Socket;
import java.io.*;
import java.util.*;

/**
 * SocketClient is the class of client that communicates to the server using the socket
 */
public class SocketClient extends AbstactClientType {
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
    public SocketClient(int port,String ip){
        superInitialization(port,ip);
    }

    /**
     * override of the superMethod
     * @throws IOException if the connection had failed
     */
    public void connect() throws IOException {
        socketClient = new Socket(getPort(),getId());
        System.out.println("Connection established");

    }



}
