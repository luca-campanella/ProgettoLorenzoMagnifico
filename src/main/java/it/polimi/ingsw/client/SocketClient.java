package it.polimi.ingsw.client;


import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.gamelogic.Player.FamilyMemberColor;
import it.polimi.ingsw.packet.ErrorType;
import it.polimi.ingsw.packet.LoginOrRegisterPacket;
import it.polimi.ingsw.packet.MoveInTowerPacket;
import it.polimi.ingsw.packet.PacketType;
import it.polimi.ingsw.utils.Debug;

import java.io.*;
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
    private ObjectInputStream inStream;
    /**
     * Informations delivered to the server
     */
    private ObjectOutputStream outStream;
    private ErrorType response;

    /**
     *Initialization of the attributes on the superclass
     */
    public SocketClient (ClientMain controllerMain, String serverAddress, int port)throws IOException{
        super(controllerMain, serverAddress, port);
        Debug.printVerbose("New SocketClient created");
        inStream = new ObjectInputStream(new BufferedInputStream(socketClient.getInputStream()));
        outStream = new ObjectOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
    }
    /**
     * override of the superMethod
     * @throws ClientConnectionException if the connection had failed
     */
    @Override
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
     * this method is used when an user already exists and decides to login with his username and password
     *
     * @param nickname
     * @param password
     * @throws NetworkException if something goes wrong during the connection
     * @throws LoginException   if username doesn't exist or if password is wrong
     */
    @Override
    public void loginPlayer(String nickname, String password) throws NetworkException, LoginException {
        try {
            outStream.writeObject(PacketType.LOGIN);
            outStream.writeObject(new LoginOrRegisterPacket(nickname, password));
            outStream.flush();
            response=(ErrorType)inStream.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("Connection not avaiable",e);
            throw new NetworkException(e);
        }
        if(response==ErrorType.ALREADY_LOGGED_TO_ROOM ||
                response==ErrorType.NOT_EXISTING_USERNAME ||
                         response==ErrorType.WRONG_PASSWORD){
            throw new LoginException(response);
        }
    }

    /**
     * this method is used when the user has never played and wants to create an account
     *
     * @param nickname to register in the server DB
     * @param password to register in the server DB
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void registerPlayer(String nickname, String password) throws NetworkException,RegisterException {
        try{
            outStream.writeObject(PacketType.REGISTER);
            outStream.writeObject(new LoginOrRegisterPacket(nickname,password));
            outStream.flush();
            response=(ErrorType)inStream.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("connessione non disponibile",e);
            throw new NetworkException(e);
        }
        if(response==ErrorType.ALREADY_EXISTING_USERNAME){
            throw new RegisterException();
        }
    }



    /**
     * this method is used to discard a leader card
     * @param nameLeader is the name of the card
     * @param resourceChoose is the resource chose to obtain when the leader is sacrificed
     */
    @Override
    public void discardCard(String nameLeader, String resourceChoose){
        //TODO
    };

    /**
     * this method is used to move the family member on the towers
     * @param familyMemberColor color of the member
     * @param servantUsed number servant used on the member
     * @param numberTower number of the tower
     * @param floorTower floor of the tower
     */
    public void moveInTower(FamilyMemberColor familyMemberColor, int servantUsed, int numberTower, int floorTower)
            throws NetworkException,IllegalMoveException {
        try{
            outStream.writeObject(PacketType.MOVE_IN_TOWER);
            outStream.writeObject(new MoveInTowerPacket(familyMemberColor,servantUsed,numberTower,floorTower));
            outStream.flush();
            response=(ErrorType)inStream.readObject();
        }
        catch (IOException |ClassNotFoundException e){
            throw new NetworkException(e);
        }
        if(response==ErrorType.LOW_RESOURCES || response== ErrorType.LOW_VALUE_DICE){
            throw new IllegalMoveException(response);
        }
    }

    /**
     * this method is used to inform the room that the player had ended his phase
     */
    @Override
    public void endPhase(){
        //TODO
    };

    protected ClientMain getControllerMain() {
        return super.getControllerMain();
    }



}
