package it.polimi.ingsw.server;

import it.polimi.ingsw.client.RMIClientInterface;
import it.polimi.ingsw.exceptions.FullRoomException;
import it.polimi.ingsw.exceptions.GameAlreadyStartedRoomException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.ServerException;
import it.polimi.ingsw.utils.Debug;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

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

    /**
     * this method is used when the user has never played and wants to create an account
     * @param nickname to register in the server DB
     * @param password to register in the server DB
     * @return
     * @throws RemoteException if something goes wrong during the connection
     */
    @Override
	public String loginPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException
	{
		//TODO implement
        Debug.printDebug("CLient tried to log in, usr: " + nickname + "password: " + password);
        //TODO implement controls over the existing players (in another class)

        //TODO implement creation of room (in another class)
        Room room = new Room(4, 3000);
        try {
            room.addNewPlayer(new RMIPlayer(nickname));
        } catch (FullRoomException e) {
            //TODO handle exception
            e.printStackTrace();
        } catch (GameAlreadyStartedRoomException e) {
            //TODO handle exception
            e.printStackTrace();
        }
        return "test";
	}

    /**
     * this method is used when an user already exists and decides to login with his username and password, real implementation of the abstract method
     *
     * @param nickname
     * @param password
     * @throws RemoteException
     * @throws LoginException   if username doesn't exist or if password is wrong
     */
    @Override
    public String registerPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException
    {
        //TODO implement
        Debug.printDebug("CLient tried to register, usr: " + nickname + "password: " + password);
        return "test";
    }

    private void connectClientToPlayer()
    {
        String randomRMIName = UUID.randomUUID().toString();
    }
}	
