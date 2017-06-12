package it.polimi.ingsw.client.network;

import it.polimi.ingsw.client.controller.ClientInterface;
import it.polimi.ingsw.client.exceptions.ClientConnectionException;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;

/**
 * This is the abstract class that represent the network part of the client, can be extended by RMIClient or SocketClient, depending on which connection the user chooses
 */
public abstract class AbstractClientType  {

    /**
     * the instance of the server to call the callback functions and communicate from server to client
     */
    private ClientInterface controllerMain;

    /**
     * The address of the server to connect to
     */
    private String serverAddress;

    /**
     * the port of the server to connect to
     */
    private int port;


    public abstract void connect() throws ClientConnectionException;

    public AbstractClientType(ClientInterface controllerMain, String serverAddress, int port) {
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
     */
    public abstract void discardCard(String nameLeader, String resourceChoose) throws NetworkException;

    /**
     * this method is used to inform the room that the player had ended his phase
     */
    public abstract void endPhase() throws NetworkException;

    /**
     * This method is used by the client to send chat message to all other players in the room (Direction: client -> sever)
     * @param msg The message
     * @throws NetworkException
     */
    public abstract void sendChatMsg(String msg) throws NetworkException;

    protected ClientInterface getControllerMain() {
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

}
