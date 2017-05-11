package it.polimi.ingsw.Server;

import debug.Debug;
import exceptions.ServerException;

/**
 * Server is the main class of the server side of the application. On startup the server creates two objects: RMIServer and SocketServer passing them the reference to himself in order to let them call himself.
 */
public class ServerMain {
	
	/**
	 * The static port used for rmi connections
	 */
	private static final int RMI_PORT = 3034;
	/**
	 * The static port used for socket connections
	 */
	private static final int SOCKET_PORT = 3035;
	
	/**
	 * RMIServer instance, used to create the real RMI server that publishes an object
	 */
	RMIServer RMIServerInst;
	/**
	 * SocketServer instance, used to create the real Socket server that opens a socket and listens for connections
	 */
	//SocketServer SocketServerInst;
	
	/**
	 * Private constructor to initialize the class
	 */
	private ServerMain()
	{
		Debug.instance(Debug.LEVEL_NORMAL);
		try {
		startServer();
		} catch(ServerException e) {
			Debug.printError(e);
		}
	}
	
	public static void main(String[] args) {
		new ServerMain();		
	}
	
	/**
	 * Starts the real servers
	 * @throws ServerException: in case either of the servers or both don't start
	 */
	private void startServer() throws ServerException
	{
		RMIServerInst = new RMIServer(this, RMI_PORT);

		//SocketServerInst = new SocketServer(this, SOCKET_PORT);
		
	}

}
