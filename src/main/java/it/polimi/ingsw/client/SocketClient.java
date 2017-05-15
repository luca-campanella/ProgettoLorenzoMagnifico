package it.polimi.ingsw.client;


import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * SocketClient is the class of client that communicates to the server using the socket
 */
public class SocketClient extends AbstractClientType {
    /**
     *socket connected to the server
     */
    private Socket socketClient;
    /**
     * Information getted from the server
     */
    private ObjectInputStream inputInformation;
    /**
     * Informations delivered to the server
     */
    private ObjectOutputStream outputInformation;
    /**
     *Initialization of the attributes on the superclass
     */
    public SocketClient(ClientMain controllerMain, String serverAddress, int port){
        super(controllerMain, serverAddress, port);
    }

    /**
     * this method is used when an user already exists and decides to login with his username and password
     *
     * @param nickname
     * @param password
     * @throws NetworkException if something goes wrong during the connection
     * @throws LoginException   if username doesn't exist or if password is wrong
     */
    @Override
    public void loginPlayer(String nickname, String password) throws NetworkException, LoginException {
        //TODO implement the method loginPlayer in socket
    }

    /**
     * this method is used when the user has never played and wants to create an account
     *
     * @param nickname to register in the server DB
     * @param password to register in the server DB
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void registerPlayer(String nickname, String password) throws NetworkException {
        //TODO implement the method loginPlayer in socket
    }

    /**
     * override of the superMethod
     * @throws ClientConnectionException if the connection had failed
     */
    public void connect() throws ClientConnectionException {
        try{
            socketClient = new Socket(getServerAddress(),getPort());
        }
        catch(IOException e){
            Debug.printError("Cannot connect Socket client",e);
            throw new ClientConnectionException(e);
        }

    }
    /**
     * this method is used to deliver a move
     * @param colorFamilyMember color of the family member moved
     * @param servantUsed number of servant used to increase the value of the family member
     * @param numberPlace the number of the place where to move the family member
     */
    public void doMove(String colorFamilyMember, int servantUsed, int numberPlace){}
    //TODO;

    /**
     * this method is used to discard a leader card
     * @param nameLeader is the name of the card
     * @param resourceChoose is the resource chose to obtain when the leader is sacrificed
     */
    public void discardCard(String nameLeader, String resourceChoose){
        //TODO
    };

    /**
     * this method is used to inform the room that the player had ended his phase
     */
    public void endPhase(){
        //TODO
    };

    protected ClientMain getControllerMain() {
        return super.getControllerMain();
    }



}
