package it.polimi.ingsw.client.network;

import it.polimi.ingsw.client.controller.NetworkControllerClientInterface;
import it.polimi.ingsw.client.exceptions.*;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;

import java.util.HashMap;

/**
 * This is the abstract class that represent the network part of the client, can be extended by RMIClient or SocketClient, depending on which connection the user chooses
 */
public abstract class AbstractClientType  {

    /**
     * the instance of the server to call the callback functions and communicate from server to client
     */
    private NetworkControllerClientInterface controllerMain;

    /**
     * The address of the server to connect to
     */
    private String serverAddress;

    /**
     * the port of the server to connect to
     */
    private int port;


    public abstract void connect() throws ClientConnectionException;

    public AbstractClientType(NetworkControllerClientInterface controllerMain, String serverAddress, int port) {
        this.controllerMain = controllerMain;
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     * this method is used when an user already exists and decides to login with his username and password
     * @param nickname
     * @param password
     * @throws NetworkException if something goes wrong during the connection
     * @throws LoginException if username doesn't exist or if password is wrong
     */
    public abstract void loginPlayer(String nickname, String password) throws NetworkException, LoginException;

    /**
     * this method is used when the user has never played and wants to create an account
     * @param nickname to register in the server DB
     * @param password to register in the server DB
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void registerPlayer(String nickname, String password) throws NetworkException,UsernameAlreadyInUseException;

    /**
     * this method is used to discard a leader card
     * @param nameLeader is the name of the card
     * @param resourceChoose is the resource chose to obtain when the leader is sacrificed
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void discardLeaderCard(String nameLeader, HashMap<String, Integer> resourceChoose) throws NetworkException;

    /**
     * this method is used to inform the room that the player had ended his phase
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void endPhase() throws NetworkException;

    /**
     * This method is used by the client to send chat message to all other players in the room (Direction: client -> sever)
     * @param msg The message
     * @throws NetworkException
     */
    public abstract void sendChatMsg(String msg) throws NetworkException;

    protected NetworkControllerClientInterface getControllerMain() {
        return controllerMain;
    }

    protected String getServerAddress() {
        return serverAddress;
    }

    protected int getPort() {
        return port;
    }

    /**
     * this method is used to return to the controller main the nickname of the player on the game
     * the controller represents this player
     */
    public void returnNickname(String nickname){

        controllerMain.setNickname(nickname);

    }

    /**
     * this method is used to deliver the leader the client has choose tho keep
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void deliverLeaderChose(LeaderCard leaderCard) throws NetworkException;

    /**
     * this method is used to deliver a leader card that the client wants to play
     * @param nameLeader the name of the chosen leader
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void playLeaderCard(String nameLeader, HashMap<String, String> choicesOnCurrentActionString) throws NetworkException;

    /**
     * this method is used to deliver the move of a family member on a tower
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void placeOnTower(FamilyMember familyMember, int numberTower, int floorTower, HashMap<String, Integer> playerChoices)
            throws NetworkException;

    /**
     * this method is used to deliver the move of a family member on a space market
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void placeOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices)
            throws NetworkException;

    public abstract void placeOnCouncil(FamilyMember familyMember,HashMap<String, Integer> playerChoices)
        throws NetworkException;
    /**
     * this method is used to deliver the move of a family member on harvest
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void harvest (FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices) throws NetworkException;

    /**
     * this method is used to deliver the move of a family member on build
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     */
    public abstract void build (FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices)
            throws NetworkException;

    /**
     * this method is called by the client to deliver to the server the tile chosen by the player
     * @param tileChosen
     */
    public abstract void deliverTileChosen(PersonalTile tileChosen) throws NetworkException;

}
