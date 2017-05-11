package it.polimi.ingsw.server;

import exceptions.ServerException;

/**
 * This abstract class gives general directions to how RMIServer and SocketServer should be built
 */
public abstract class AbstractServerType {
	/**
	 * The instance of the main server class in order to be able to call methods on it, like loginPlayer
	 */
	protected ServerMain serverMainInst;
	protected int port;
	
	/**
	 * Creates a new server 
	 * @param serverMainInst: the main server class
	 * @param port: the port to start the server to
	 */
	public AbstractServerType(ServerMain serverMainInst, int port) {
		this.serverMainInst = serverMainInst;
		this.port = port;
	}
	
	/**
	 * Performs the real connection stuff
	 * @throws ServerException if a problem is encountered starting the server
	 */
	private abstract void startServer() throws ServerException;
	
	/**
	 * Closes all connections
	 * This method should be called only when you want to shut down the server completely and terminate the program
	 * @throws ServerException if a problem is encountered closing the server
	 */
	public abstract void closeServer() throws ServerException;
}
