package it.polimi.ingsw.client.network.socket;


import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.client.exceptions.*;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;
import it.polimi.ingsw.client.network.socket.packet.*;
import it.polimi.ingsw.client.network.socket.protocol.ReadServerPacketProtocol;
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
    private RegisterErrorEnum response;

    /**
     * the protocol used to read packet sedf by the server
     */
    private ReadServerPacketProtocol readPacket;

    /**
     *Initialization of the attributes on the superclass
     */
    public SocketClient (ClientMain controllerMain, String serverAddress, int port) {
        super(controllerMain, serverAddress, port);
        Debug.printVerbose("New SocketClient created");
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
            Debug.printError("Cannot connect socket client",e);
            throw new ClientConnectionException(e);
        }

        try {
            inStream = new ObjectInputStream(new BufferedInputStream(socketClient.getInputStream()));
            outStream = new ObjectOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
            outStream.flush();
        } catch (IOException e) {
            Debug.printError("Cannot open socket streams", e);
            throw new ClientConnectionException("Cannot open socket streams", e);
        }
        readPacket= new ReadServerPacketProtocol(this);

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

        LoginErrorEnum loginResponse;

        try {
            outStream.writeObject(PacketType.LOGIN);
            outStream.writeObject(new LoginOrRegisterPacket(nickname, password));
            outStream.flush();
            loginResponse = (LoginErrorEnum) inStream.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("Connection not avaiable",e);
            throw new NetworkException(e);
        }
        if(loginResponse==LoginErrorEnum.ALREADY_LOGGED_TO_ROOM ||
                loginResponse==LoginErrorEnum.NOT_EXISTING_USERNAME ||
                loginResponse==LoginErrorEnum.WRONG_PASSWORD){
            throw new LoginException(loginResponse);
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
    public void registerPlayer(String nickname, String password) throws NetworkException,UsernameAlreadyInUseException {
        try{
            outStream.writeObject(PacketType.REGISTER);
            outStream.writeObject(new LoginOrRegisterPacket(nickname,password));
            outStream.flush();
            response=(RegisterErrorEnum)inStream.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("connection not avaiable",e);
            throw new NetworkException(e);
        }
        if(response== RegisterErrorEnum.ALREADY_EXISTING_USERNAME){
            throw new UsernameAlreadyInUseException("Username already in use");
        }
    }
    public void playCard(String nameLeader) throws NetworkException{
        try{
            outStream.writeObject(PacketType.PLAY_LEADER);
            outStream.writeObject(new PlayCardPacket(nameLeader));
            outStream.flush();
        }
        catch(IOException e){
            Debug.printError("network is not avaiable",e);
            throw new NetworkException(e);

        }
    }



    /**
     * this method is used to discard a leader card
     * @param nameLeader is the name of the card
     * @param resourceChoose is the resource chose to obtain when the leader is sacrificed
     */
    @Override
    public void discardCard(String nameLeader, String resourceChoose) throws NetworkException{

        try {
            outStream.writeObject(PacketType.DISCARD_LEADER);
            outStream.writeObject(new DiscardCardPacket(nameLeader, resourceChoose));
            outStream.flush();
        }
        catch (IOException e) {
            Debug.printError("Cannot write object to output socket stream", e);
            throw new NetworkException("Cannot write object to outputo socket stream", e);
        }

    };

    /**
     * this method is used to move the family member on the towers
     * @param familyMemberColor color of the member
     * @param servantUsed number servant used on the member
     * @param numberTower number of the tower
     * @param floorTower floor of the tower
     */
    public void moveInTower(DiceAndFamilyMemberColor familyMemberColor, int servantUsed, int numberTower, int floorTower)
            throws NetworkException,IllegalMoveException {
        MoveErrorEnum moveErrorEnum;
        try{
            outStream.writeObject(PacketType.MOVE_IN_TOWER);
            outStream.writeObject(new MoveInTowerPacket(familyMemberColor,servantUsed,numberTower,floorTower));
            outStream.flush();
            moveErrorEnum=(MoveErrorEnum) inStream.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            Debug.printError("Connection not avaiable",e);
            throw new NetworkException(e);
        }
        if(moveErrorEnum==MoveErrorEnum.LOW_RESOURCES || moveErrorEnum== MoveErrorEnum.LOW_VALUE_DICE){
            throw new IllegalMoveException(moveErrorEnum);
        }
    }

    /**
     * this method is used when the family member in moved on a generic market space
     */
    public void moveInMarket(DiceAndFamilyMemberColor familyMemberColor, int servantUsed, int placeNumber)
            throws NetworkException,IllegalMoveException{
        MoveErrorEnum moveErrorEnum;
        try{
            outStream.writeObject(PacketType.MOVE_IN_MARKET);
            outStream.writeObject(new MoveInMarketPacket(familyMemberColor, servantUsed, placeNumber));
            outStream.flush();
            moveErrorEnum=(MoveErrorEnum)inStream.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not available",e);
            throw new NetworkException(e);
        }
        if(moveErrorEnum==MoveErrorEnum.LOW_RESOURCES || moveErrorEnum== MoveErrorEnum.LOW_VALUE_DICE){
            throw new IllegalMoveException(moveErrorEnum);
        }
    }

    /**
     * this method is called when the family member is moved on the harvest space
     */
    public void harvesting (DiceAndFamilyMemberColor familyMemberColor, int servantUsed) throws NetworkException{
        try{
            outStream.writeObject(PacketType.HARVESTING);
            outStream.writeObject(new BuildOrHarvestPacket(familyMemberColor,servantUsed));
            outStream.flush();
        }
        catch (IOException e){
            Debug.printError("network is not avaiable", e);
            throw new NetworkException(e);
        }

    }
    public void building (DiceAndFamilyMemberColor familyMemberColor, int servantUsed) throws NetworkException{
        try{
            outStream.writeObject(PacketType.BUILDING);
            outStream.writeObject(new BuildOrHarvestPacket(familyMemberColor,servantUsed));
            outStream.flush();
        }
        catch (IOException e){
            Debug.printError("network is not avaiable", e);
            throw new NetworkException(e);
        }

    }


    /**
     * this method is used to inform the room that the player had ended his phase
     */
    @Override
    public void endPhase() throws NetworkException{
        try{
            outStream.writeObject(PacketType.END_PHASE);
            outStream.flush();
        }
        catch (IOException e){
            Debug.printError("network is not avaiable", e);
            throw new NetworkException(e);
        }
    }

    /**
     * This method is used to send chat message to all players in the room
     *
     * @throws NetworkException
     */
    @Override
    public void sendChatMsg(String msg) throws NetworkException {
        try{
            outStream.writeObject(PacketType.CHAT);
            outStream.writeObject(msg);
            outStream.flush();
        }
        catch (IOException e){
            Debug.printError("network is not available", e);
            throw new NetworkException(e);
        }
    }

    public void receiveChatMsg() throws NetworkException{
        try{
            ChatReceivedPacket packet = (ChatReceivedPacket)inStream.readObject();

            getControllerMain().receiveChatMsg(packet.getSenderNickname(), packet.getMessage());
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not available",e);
            throw new NetworkException(e);
        }
    }
    public void receiveUpdates(){
        //TODO

    }

}
