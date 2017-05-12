package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.ServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     *
     * @param serverMainInst
     * @param port
     */
    public SocketServer(ServerMain serverMainInst, int port) {

        super(serverMainInst, port);
    }

    /**
     * Performs the real connection stuff
     *
     * @throws ServerException if a problem is encountered starting the server
     */
    @Override
    protected void startServer() throws ServerException, ServerException {
        //TODO implement the method startServer in socket
    }

    /**
     * Closes all connections
     * This method should be called only when you want to shut down the server completely and terminate the program
     *
     * @throws ServerException if a problem is encountered closing the server
     */
    @Override
    public void closeServer() throws ServerException {
        //TODO implement the method closeServer in socket
    }

    public void startServerSocket(){

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
        catch(IOException e)

        {
            System.err.println(e.getMessage());
            return;
        }
        /**
        * connect with a loop all the possible clients
        */
        while(true)

        {
            try {
                Socket socket = socketServer.accept();
                generetorOfConnection.submit(new SocketPlayer(socket));
            }
            /**
            * error occured if the server shuts down
            */ catch (IOException e) {
                break;
            }

            generetorOfConnection.shutdown();
        }
    }
}