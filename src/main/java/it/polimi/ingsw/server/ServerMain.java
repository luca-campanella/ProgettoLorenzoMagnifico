package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.Debug;
import it.polimi.ingsw.exceptions.ServerException;

/**
 * server is the main class of the server side of the application. On startup the server creates two objects: RMIServer and SocketServer passing them the reference to himself in order to let them call himself.
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
	 * Constant that specifies the maximum numbers of players in a game (default set to 4, set to 5 to extend functionalities)
	 */
	private static final int MAX_N_OF_PLAYERS = 4;

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


	public static int getMaxNOfPlayers() {
		return MAX_N_OF_PLAYERS;
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
