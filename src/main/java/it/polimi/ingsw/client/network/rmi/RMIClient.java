package it.polimi.ingsw.client.network.rmi;

import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.client.exceptions.ClientConnectionException;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.controller.network.rmi.RMIPlayerInterface;
import it.polimi.ingsw.controller.network.rmi.RMIServerInterface;
import it.polimi.ingsw.utils.Debug;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The class to handle the rmi client side, publishes itself and passes its reference to the controller, so the controller can call it back with RMIClientInterface
 */
public class RMIClient extends AbstractClientType implements RMIClientInterface {

    private Registry registry;
    private RMIServerInterface RMIServerInterfaceInst;
    private RMIPlayerInterface RMIPlayerInterfaceInst;

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
            RMIPlayerInterfaceInst = RMIServerInterfaceInst.loginPlayer(nickname, password, this);
        } catch(RemoteException e) {
            Debug.printError("Cannot login player due to a network problem on rmi");
            throw new NetworkException(e);
        }

        try {
            Debug.printVerbose("Got the remote object to controll the player " + RMIPlayerInterfaceInst.getNickname());
        } catch (RemoteException e) {
            Debug.printError("Cannot use the passed interface");
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used when the user has never played and wants to create an account, real implementation of the abstract method
     *
     * @param nickname to register in the controller DB
     * @param password to register in the controller DB
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void registerPlayer(String nickname, String password) throws NetworkException,UsernameAlreadyInUseException {
        try {
            RMIPlayerInterfaceInst = RMIServerInterfaceInst.registerPlayer(nickname, password, this);
        } catch(RemoteException e) {
            Debug.printError("Cannot register player due to a network problem on rmi");
            throw new NetworkException(e);
        }
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
     * This method is used to send chat message to all players in the room
     *
     * @param msg The message
     * @throws NetworkException
     */
    @Override
    public void sendChatMsg(String msg) throws NetworkException {
        try {
            RMIPlayerInterfaceInst.sendChatMsg(msg);
        } catch (RemoteException e) {
            throw new NetworkException("rmi problem with chat message", e);
        }
    }

    /**
     * Performs the rmi operations to get "open" a rmi connection with the controller
     * @throws ClientConnectionException if it can't find either the sever either the controller class or it can't pulbish itself on the registry
     */
    @Override
    public void connect() throws ClientConnectionException {
        try {
            registry = LocateRegistry.getRegistry(getServerAddress(), getPort());
            RMIServerInterfaceInst = (RMIServerInterface) registry.lookup("RMIServerInterface");
            UnicastRemoteObject.exportObject(this, 0); //with 0 exports the object on a random port
            Debug.printDebug("rmi Client connected succesfully");

        } catch(RemoteException | NotBoundException e) {
            Debug.printError("Cannot connect rmi client", e);
            throw new ClientConnectionException(e);
        }

    }

    /**
     * This method is called from the controller to communicate that a chat message has arrived to the client (Direction: controller -> client)
     * @param senderNick the nickname of the player who sent the msg
     * @param msg
     * @throws RemoteException
     */
    @Override
    public void receiveChatMsg(String senderNick, String msg) throws RemoteException {
        getControllerMain().receiveChatMsg(senderNick, msg);
    }
}