package server

import java.net.Socket;
import java.io.*;
import java.util.*;

/**
 * SocketServer is the class of Server that manage the login of the client using socket
 */
public class SocketServer extends AbstractServerType {
    /**
     * this executor creates a thread when the client log in
     */
    ExecutorService generetorOfConnection = Executors.newCachedThreadPool();
    /**
     * socket on the server that is used by the client to log in
     */
    ServerSocket socketServer;

    /**
     * method used to menage different connections with the clients
     *
     * @param port used to connect
     */
    public SocketServer(int port) {
        super(port);
    }
    /**
     * control if the port is available
     */
    try

    {
        socketServer = new ServerSocket(getPort());
    }
    /**
     * manage the unavailability of the port
     */
    catch(
    IOException e)

    {
        System.err.println(e.getMessage());
        return;
    }
    System.out.println("The server is ready");
    /**
     * connect with a loop all the possible clients
     */
    while(true)

    {
        try {
            Socket socket = socketServer.accept();
            executor.submit(new SocketPlayer(socket));
        }
        /**
         * error occured if the server shuts down
         */ catch (IOException e) {
            break;
        }

        executor.shutdown();
    }
}