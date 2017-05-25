package it.polimi.ingsw.controller.network.socket;

import it.polimi.ingsw.controller.AbstractServerType;
import it.polimi.ingsw.controller.ServerMain;
import it.polimi.ingsw.client.exceptions.ServerException;
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
     * socket on the controller that is used by the client to log in
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
        Debug.printVerbose("socket server started successfully");
    }


    /**
     * Closes all connections
     * This method should be called only when you want to shut down the controller completely and terminate the program
     *
     * @throws ServerException if a problem is encountered closing the controller
     */
    @Override
    public void closeServer() throws ServerException {
        //TODO implement the method closeServer in socket
    }

    /**
     * Performs the real connection stuff
     *
     * @throws ServerException if a problem is encountered starting the controller
     */
    @Override
    protected void startServer() throws ServerException {

        /* start the main socket to accept connections. Control if the port is available */
        try {
            socketServer = new ServerSocket(getPort());
        }
        catch(IOException e) // manage the unavailability of the port
        {
            Debug.printError("Cannot start socket controller, socket main port already in use?", e);
            throw new ServerException("Problem starting socket controller (probably port alrady in use)", e);
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
            Socket socket;
            generetorOfConnection = Executors.newCachedThreadPool();

            Debug.printVerbose("Process waiting for socket clients started");
            /* infinite loop to wait for all the possible clients */
            while(true)
            {
                try {
                    socket = socketServer.accept();
                    Debug.printVerbose("New socket accepted from " + socket.getInetAddress());
                } catch (IOException e) //error occurred if the controller shuts down
                 {
                     Debug.printError("Can't accept new connection on socket controller, controller shuts down", e);
                    break;
                }
                try {
                    Debug.printVerbose("creazione  player");
                    SocketPlayer player = new SocketPlayer(socket, getServerMainInst());
                    Debug.printVerbose("Constructor of socketplayer called successfully");
                    generetorOfConnection.submit(player);
                    System.out.println("creazione  player con successo");
                    System.out.flush();
                } catch (IOException e) {
                    Debug.printError("Can't open input and ouput streams on socket, closing socket", e);
                    try {
                        socket.close();
                    } catch (IOException e1) {
                        Debug.printError("Can't even close the socket, letting the client handle this", e);
                        //TODO check that the clients handles this case correctly, the controller can't open in and output streams
                    }
                }
            }
            generetorOfConnection.shutdown();
        }
    }
}