package it.polimi.ingsw.client.network.rmi;

import it.polimi.ingsw.client.controller.NetworkControllerClientInterface;
import it.polimi.ingsw.client.exceptions.*;
import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
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
    /**
     * this method is used to deliver a leader card that the client wants to play
     * @param nameLeader the name of the chosen leader
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void playLeaderCard(String nameLeader) throws NetworkException {
        try {
            RMIPlayerInterfaceInst.playLeaderCard(nameLeader);
        } catch (RemoteException e) {
            Debug.printError("RMI: Cannot deliver the leader card chosen to the server", e);
            throw new NetworkException("RMI: Cannot deliver the leader card chosen to the server", e);
        }
    }

    /**
     * this method is used to deliver the move of a family member on a tower
     * @param familyMember the chosen family member
     * @param numberTower the tower index (from left to right)
     * @param floorTower the number of the floor (from top to bottom)
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     * @throws IllegalMoveException //todo remove or implement server side
     */
    @Override
    public void placeOnTower(FamilyMember familyMember, int numberTower, int floorTower, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {
        RMIPlayerInterfaceInst.placeOnTower(familyMember.getColor(), numberTower, floorTower, playerChoices);
    }

    /**
     * this method is used to deliver the move of a family member on a marketplace
     * @param familyMember is the family member chosen
     * @param marketIndex is the index of the merket (from left to right) todo: check this assertion --Arto
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     * @throws IllegalMoveException //todo remove or implement server side
     */
    @Override
    public void placeOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {
        RMIPlayerInterfaceInst.placeOnMarket(familyMember.getColor(), marketIndex, playerChoices);
    }

    /**
     * this method is used to deliver the placement a family member on council
     * @param familyMember is the family member chosen
     * @param playerChoices is a map that contains all the choices of the client when af effect is asked
     * @throws NetworkException if something goes wrong during the connection
     * @throws IllegalMoveException //todo
     */
    @Override
    public void placeOnCouncil(FamilyMember familyMember, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {
        RMIPlayerInterfaceInst.placeOnCouncil(familyMember.getColor(), playerChoices);
    }

    /**
     * this method is used to deliver the information about the harvest of a player
     * @param familyMember is the family member chosen
     * @param servantUsed is the number of family member used to increase the power of the action
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something foes wrong with the connection
     * @throws IllegalMoveException //todo
     */
    @Override
    public void harvest(FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {
        RMIPlayerInterfaceInst.harvest(familyMember.getColor(), servantUsed, playerChoices);
    }

    /**
     * this method is used to deliver the information that a player has built
     * @param familyMember is the family member used to build
     * @param servantUsed is the number of family member used to increase the power of the action
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong with the connection
     * @throws IllegalMoveException //todo
     */
    @Override
    public void build(FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {
        RMIPlayerInterfaceInst.build(familyMember.getColor(), servantUsed, playerChoices);
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

    /**
     * this method is called by the server to notify that another player has moved on a tower
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param towerIndex        the index of the tower he placed the family member in
     * @param floorIndex        the index of the floor he placed the family member in
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    @Override
    public void receivePlaceOnTower(String playerNickname, DiceAndFamilyMemberColorEnum familyMemberColor, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws RemoteException {
        getControllerMain().receivedPlaceOnTower(playerNickname, familyMemberColor, towerIndex, floorIndex, playerChoices);
    }

    /**
     * this method is called by the server to notify that another player has moved in a market action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param marketIndex       the index of the market action space he placed the family member in
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    @Override
    public void receivePlaceOnMarket(String playerNickname,
                                     DiceAndFamilyMemberColorEnum familyMemberColor,
                                     int marketIndex,
                                     HashMap<String, Integer> playerChoices) throws RemoteException {
        getControllerMain().receivedPlaceOnMarket(playerNickname, familyMemberColor, marketIndex, playerChoices);

    }

    /**
     * this method is called by the server to notify that another player has moved in the harvest action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param servantsUsed      the number of servants used to perform the action
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    @Override
    public void receiveHarvest(String playerNickname,
                               DiceAndFamilyMemberColorEnum familyMemberColor,
                               int servantsUsed,
                               HashMap<String, Integer> playerChoices) throws RemoteException {
        getControllerMain().receivedHarvest(playerNickname, familyMemberColor, servantsUsed, playerChoices);
    }

    /**
     * this method is called by the server to notify that another player has moved in the build action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param servantsUsed      the number of servants used to perform the action
     * @param playerChoices     the hashmap with his choices correlated with this action
     */
    @Override
    public void receiveBuild(String playerNickname,
                               DiceAndFamilyMemberColorEnum familyMemberColor,
                               int servantsUsed,
                               HashMap<String, Integer> playerChoices) throws RemoteException {
        getControllerMain().receivedBuild(playerNickname, familyMemberColor, servantsUsed, playerChoices);
    }

    /**
     * this method is called by the server to notify that another player has moved in the council
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param playerChoices     the hashmap with his choices correlated with this action
     */
    @Override
    public void receivePlaceOnCouncil(String playerNickname,
                             DiceAndFamilyMemberColorEnum familyMemberColor,
                             HashMap<String, Integer> playerChoices) throws RemoteException {
        getControllerMain().receivedPlaceOnCouncil(playerNickname, familyMemberColor, playerChoices);
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

    /**
     * This method is called to deliver the cards to the player, order should be consistent
     * @param cardsToPlace the list of cards to place
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    @Override
    public void receiveCardToPlace(ArrayList<AbstractCard> cardsToPlace) throws RemoteException {
            getControllerMain().receiveCardsToPlace(cardsToPlace);
    }
}
