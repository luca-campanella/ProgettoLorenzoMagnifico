package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.server.RMIServerInterface;
import it.polimi.ingsw.utils.Debug;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

/**
 * The class to handle the RMI client side, publishes itself and passes its reference to the server, so the server can call it back with RMIClientInterface
 */
public class RMIClient extends AbstractClientType implements RMIClientInterface {

    private Registry registry;
    private RMIServerInterface RMIServerInterfaceInst;

    /**
     * Constructor of RMIClient, it should be called before connect(), sets the parmeters using the super constructor of AbstractClientType
     * @param controllerMain the istance of ClientMain, to call callback functions
     * @param serverAddress the address to connect to
     * @param port the port to connect to
     */
    public RMIClient(ClientMain controllerMain, String serverAddress, int port) {
        super(controllerMain, serverAddress, port);
    }

    /**
     * this method is used when an user already exists and decides to login with his username and password, real implementation of the abstract method
     *
     * @param nickname
     * @param password
     * @throws NetworkException if something goes wrong during the connection
     * @throws LoginException   if username doesn't exist or if password is wrong
     */
    @Override
    public void loginPlayer(String nickname, String password) throws NetworkException, LoginException {
        try {
            RMIServerInterfaceInst.loginPlayer(nickname, password, this);
        } catch(RemoteException e) {
            Debug.printError("Cannot ligin player due to a network problem on RMI");
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used when the user has never played and wants to create an account, real implementation of the abstract method
     *
     * @param nickname to register in the server DB
     * @param password to register in the server DB
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void registerPlayer(String nickname, String password) throws NetworkException {
        try {
            RMIServerInterfaceInst.registerPlayer(nickname, password, this);
        } catch(RemoteException e) {
            Debug.printError("Cannot register player due to a network problem on RMI");
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used to deliver a move
     *
     * @param colorFamilyMember color of the family member moved
     * @param servantUsed       number of servant used to increase the value of the family member
     * @param numberPlace       the number of the place where to move the family member
     */
    @Override
    public void doMove(String colorFamilyMember, int servantUsed, int numberPlace) {
        //TODO implement abstract method
    }

    /**
     * this method is used to discard a leader card
     *
     * @param nameLeader     is the name of the card
     * @param resourceChoose is the resource chose to obtain when the leader is sacrificed
     */
    @Override
    public void discardCard(String nameLeader, String resourceChoose) {
        //TODO implement abstract method
    }

    /**
     * this method is used to inform the room that the player had ended his phase
     */
    @Override
    public void endPhase() {
        //TODO implement abstract method
    }

    /**
     * Performs the RMI operations to get "open" a RMI connection with the server
     * @throws ClientConnectionException if it can't find either the sever either the server class or it can't pulbish itself on the registry
     */
    @Override
    public void connect() throws ClientConnectionException {
        try {
            registry = LocateRegistry.getRegistry(getServerAddress(), getPort());
            RMIServerInterfaceInst = (RMIServerInterface) registry.lookup("RMIServerInterface");
            UnicastRemoteObject.exportObject(this, 0); //with 0 exports the object on a random port
            Debug.printDebug("RMI Client connected succesfully");

        } catch(RemoteException | NotBoundException e) {
            Debug.printError("Cannot connect RMI client", e);
            throw new ClientConnectionException(e);
        }

    }
}
