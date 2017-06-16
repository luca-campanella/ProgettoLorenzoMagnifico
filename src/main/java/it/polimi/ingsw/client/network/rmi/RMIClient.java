package it.polimi.ingsw.client.network.rmi;

import it.polimi.ingsw.client.controller.NetworkControllerClientInterface;
import it.polimi.ingsw.client.exceptions.*;
import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.server.network.rmi.RMIPlayerInterface;
import it.polimi.ingsw.server.network.rmi.RMIServerInterface;
import it.polimi.ingsw.utils.Debug;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class to handle the rmi client side, publishes itself and passes its reference to the server, so the server can call it back with RMIClientInterface
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
    public RMIClient(NetworkControllerClientInterface controllerMain, String serverAddress, int port) {
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
            Debug.printVerbose("Got the remote object to control the player " + RMIPlayerInterfaceInst.getNickname());
        } catch (RemoteException e) {
            Debug.printError("Cannot use the passed interface");
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

    @Override
    public void deliverLeaderChose(LeaderCard leaderCard) throws NetworkException {

    }

    @Override
    public void playLeaderCard(String nameLeader) throws NetworkException {

    }

    @Override
    public void moveInTower(FamilyMember familyMember, int numberTower, int floorTower, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {

    }

    @Override
    public void moveInMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {

    }

    @Override
    public void harvest(FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {

    }

    @Override
    public void build(FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {

    }

    /**
     * Performs the rmi operations to get "open" a rmi connection with the server
     * @throws ClientConnectionException if it can't find either the sever either the server class or it can't pulbish itself on the registry
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
     * This method is called from the server to communicate that a chat message has arrived to the client (Direction: server -> client)
     * @param senderNick the nickname of the player who sent the msg
     * @param msg
     * @throws RemoteException
     */
    @Override
    public void receiveChatMsg(String senderNick, String msg) throws RemoteException {
        getControllerMain().receiveChatMsg(senderNick, msg);
    }

    @Override
    public void receivePlaceOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws RemoteException {

    }

    @Override
    public void receivePlaceOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws RemoteException {

    }

    @Override
    public void receiveBuild(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws RemoteException {

    }

    @Override
    public void receiveHarvest(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws RemoteException {

    }

    @Override
    public void receiveEndPhase(String nickname) throws RemoteException {

    }

    @Override
    public void receiveDice(ArrayList<Dice> dices) throws RemoteException {
        getControllerMain().receivedDices(dices);
    }

    @Override
    public void receiveBoard(Board gameBoard) throws RemoteException {
        getControllerMain().receivedStartGameBoard(gameBoard);
    }

    @Override
    public void receiveStartOfTurn() throws RemoteException {
        getControllerMain().receivedStartTurnNotification();
    }

    @Override
    public void receiveOrderPlayer(ArrayList<String> orderPlayers) throws RemoteException {
        getControllerMain().receivedOrderPlayers(orderPlayers);

    }

    @Override
    public void receiveNicknamePlayer(String nicknamePlayer) throws RemoteException {

    }

    @Override
    public void receiveLeaderCardChoice(ArrayList<LeaderCard> cardToPlayer) throws RemoteException {

    }

    @Override
    public void receiveCardToPlace(ArrayList<AbstractCard> cardsToPlace) throws RemoteException {

    }
}
