package it.polimi.ingsw.server;

import it.polimi.ingsw.client.RMIClientInterface;
import it.polimi.ingsw.utils.Debug;
import it.polimi.ingsw.exceptions.ServerException;


import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.*;

/**
 * RMIServer is the implementation of the server via RMI. It’s interface is used to be published and called by the client side. In fact the RMIServer publishes himself (his stub) on the RMI registry server and waits to be called by the client thru his function loginPlayer.
 */
public class RMIServer extends AbstractServerType implements RMIServerInterface {

    private Registry registry;

	/**
	 * This is the public constructor of the class that also prepares and start the server
	 * @param serverMainInst the controller object
	 * @param port
	 * @throws ServerException if the creation of the server goes wrong
	 */
	public RMIServer(ServerMain serverMainInst, int port) throws ServerException {
		super(serverMainInst, port);
		startServer();
	}

	/**
	 * performs RMI actions to create registry and publishes the object (itself)
	 * @throws ServerException if the creation of the server goes wrong
	 */
	@Override
	protected void startServer() throws ServerException
	{
		Debug.printDebug("I'm starting the RMI server on port " + getPort());
        this.createOrLoadRegistry();
        this.publishObj();
        Debug.printDebug("RMI server started");
	}

    /**
     * Creates or loads the RMI registry at the selected port
     * @throws ServerException if it cannot load or create the RMI registry
     */
	private void createOrLoadRegistry() throws ServerException
    {
        try {
            registry = LocateRegistry.createRegistry(getPort());
        } catch(RemoteException e) {
            Debug.printDebug("RMI registry already exists", e);
        }
        try {
            registry = LocateRegistry.getRegistry(getPort());
        } catch(RemoteException e) {
            Debug.printDebug("RMI registry not found", e);
            throw new ServerException("Cannot load or create the RMI registry");
        }
    }

    /**
     * publishes this class to the registry
     * @throws ServerException
     */
    private void publishObj() throws ServerException
    {
        try {
            UnicastRemoteObject.exportObject(this, getPort());
            registry.rebind("RMIServerInterface", this);
        } catch(RemoteException e) {
            Debug.printError("Unable to publish object", e);
            throw new ServerException("Cannot publish server object \"RMIServerInterface\"");
        }
    }

    /**
     * Used to close the server when it's needed no more
     * @throws ServerException
     */
    @Override
	public void closeServer() throws ServerException
	{
		//TODO implement the real closure of the server
		Debug.printDebug("I'm stopping the RMI server");
	}

    @Override
	public String loginPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException
	{
		//TODO implement
        Debug.printDebug("Player logged in");
		
		return "test";
	}
	
}	
