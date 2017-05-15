package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.ServerException;
import it.polimi.ingsw.utils.Debug;

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
     * socket on the server that is used by the client to log in
     */
    ServerSocket socketServer;

    /**
     *
     * @param serverMainInst
     * @param port
     */
    public SocketServer(ServerMain serverMainInst, int port) throws ServerException {

        super(serverMainInst, port);
        startServer();
        Debug.printVerbose("Socket serve started succesfully");
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

    /**
     * Performs the real connection stuff
     *
     * @throws ServerException if a problem is encountered starting the server
     */
    @Override
    protected void startServer() throws ServerException {

        /* start the main socket to accept connections. Control if the port is available */
        try {
            socketServer = new ServerSocket(getPort());
        }
        catch(IOException e) // manage the unavailability of the port
        {
            Debug.printError("Cannnot start Socket server, Socket main port already in use?", e);
            throw new ServerException("Problem starting socket server (probably port alrady in use)", e);
        }

        //start the thread that waits for incoming oscket connections
        new ConnectionHandler().start();
    }

    private class ConnectionHandler extends Thread {

        /**
         * this executor creates a thread when the client log in
         */
        private ExecutorService generetorOfConnection;

        @Override
        public void run() {

            generetorOfConnection = Executors.newCachedThreadPool();

            Debug.printVerbose("Process waiting for Socket clients started");
            /* infinite loop to wait for all the possible clients */
            while(true)
            {
                try {
                    Socket socket = socketServer.accept();
                    Debug.printVerbose("New socket accepted from " + socket.getInetAddress());
                    generetorOfConnection.submit(new SocketPlayer(socket));
                } catch (IOException e) //error occurred if the server shuts down
                 {
                     Debug.printError("Socket server just crashed", e);
                    break;
                }
            }
            generetorOfConnection.shutdown();
        }
    }
}