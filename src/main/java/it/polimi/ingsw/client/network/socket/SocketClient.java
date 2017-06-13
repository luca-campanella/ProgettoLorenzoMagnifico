package it.polimi.ingsw.client.network.socket;


import it.polimi.ingsw.client.controller.ClientInterface;
import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.client.exceptions.*;
import it.polimi.ingsw.client.network.socket.packet.*;
import it.polimi.ingsw.client.network.socket.protocol.ReadServerPacketProtocol;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.utils.Debug;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     * the protocol used to read packet sedf by the server
     */
    private ReadServerPacketProtocol readPacket;

    /**
     * this is the parallel thread to receive packet from the server
     */
    private ReceiveInformation receiveInformation;

    /**
     *Initialization of the attributes on the superclass
     */
    public SocketClient (ClientInterface controllerMain, String serverAddress, int port) {
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

        receiveInformation = new ReceiveInformation(inStream, readPacket);

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
            Debug.printError("Connection not available",e);
            throw new NetworkException(e);
        }
        if(loginResponse==LoginErrorEnum.ALREADY_LOGGED_TO_ROOM ||
                loginResponse==LoginErrorEnum.NOT_EXISTING_USERNAME ||
                loginResponse==LoginErrorEnum.WRONG_PASSWORD){
            throw new LoginException(loginResponse);
        }
        // start the thread to read the packet delivered by the server
        receiveInformation.start();
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
        RegisterErrorEnum response;
        try{
            outStream.writeObject(PacketType.REGISTER);
            outStream.writeObject(new LoginOrRegisterPacket(nickname,password));
            outStream.flush();
            response =(RegisterErrorEnum)inStream.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("connection not available",e);
            throw new NetworkException(e);
        }
        if(response == RegisterErrorEnum.ALREADY_EXISTING_USERNAME){
            throw new UsernameAlreadyInUseException("Username already in use");
        }
        // start the thread to read the packet delivered by the server
        receiveInformation.start();
    }

    /**
     * this method is used to deliver a leader card that the client wants to play
     * @throws NetworkException
     */
    @Override
    public void playLeaderCard(String nameLeader) throws NetworkException{
        try{
            outStream.writeObject(PacketType.PLAY_LEADER);
            outStream.writeObject(new PlayCardPacket(nameLeader));
            outStream.flush();
        }
        catch(IOException e){
            Debug.printError("network is not available",e);
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
            throw new NetworkException("Cannot write object to output on socket stream", e);
        }

    };

    /**
     * this method is used to move the family member on the towers
     * @param familyMember the family member used on the action
     * @param numberTower number of the tower
     * @param floorTower floor of the tower
     */
    @Override
    public void moveInTower(FamilyMember familyMember,
                            int numberTower, int floorTower, HashMap<String, Integer> playerChoices)
            throws NetworkException,IllegalMoveException {
        MoveErrorEnum moveErrorEnum;
        try{
            outStream.writeObject(PacketType.MOVE_IN_TOWER);
            outStream.writeObject(new PlaceOnTowerPacket(familyMember.getColor(),numberTower,floorTower, playerChoices));
            outStream.flush();
            moveErrorEnum=(MoveErrorEnum) inStream.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            Debug.printError("Connection not available",e);
            throw new NetworkException(e);
        }
        if(moveErrorEnum==MoveErrorEnum.LOW_RESOURCES || moveErrorEnum== MoveErrorEnum.LOW_VALUE_DICE
                || moveErrorEnum== MoveErrorEnum.NOT_PLAYER_TURN){
            throw new IllegalMoveException(moveErrorEnum);
        }
    }

    /**
     * this method is used when the family member in moved on a generic market space
     */
    @Override
    public void moveInMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices)
            throws NetworkException,IllegalMoveException{
        MoveErrorEnum moveErrorEnum;
        try{
            outStream.writeObject(PacketType.MOVE_IN_MARKET);
            outStream.writeObject(new PlaceOnMarketPacket(familyMember.getColor(), marketIndex, playerChoices));
            outStream.flush();
            moveErrorEnum=(MoveErrorEnum)inStream.readObject();
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not available",e);
            throw new NetworkException(e);
        }
        if(moveErrorEnum==MoveErrorEnum.LOW_RESOURCES || moveErrorEnum== MoveErrorEnum.LOW_VALUE_DICE
                || moveErrorEnum== MoveErrorEnum.NOT_PLAYER_TURN){
            throw new IllegalMoveException(moveErrorEnum);
        }
    }

    /**
     * this method is called when the family member is moved on the harvest space
     */
    @Override
    public void harvest (FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException {

        MoveErrorEnum moveErrorEnum;

        try{

            outStream.writeObject(PacketType.HARVEST);
            outStream.writeObject(new BuildOrHarvest(familyMember.getColor(),servantUsed,playerChoices));
            outStream.flush();
            moveErrorEnum=(MoveErrorEnum)inStream.readObject();

        }

        catch (IOException | ClassNotFoundException e){

            Debug.printError("network is not available", e);
            throw new NetworkException(e);

        }

        if(moveErrorEnum == MoveErrorEnum.NOT_PLAYER_TURN){

            throw new IllegalMoveException(moveErrorEnum);

        }

    }

    /**
     * this method is called when a player want to place a family member on the build
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException
     * @throws IllegalMoveException
     */
    @Override
    public void build (FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices)
            throws NetworkException, IllegalMoveException {

        MoveErrorEnum moveErrorEnum;

        try{

            outStream.writeObject(PacketType.BUILD);
            outStream.writeObject(new BuildOrHarvest(familyMember.getColor(),servantUsed,playerChoices));
            outStream.flush();
            moveErrorEnum=(MoveErrorEnum)inStream.readObject();

        }

        catch (IOException | ClassNotFoundException e){

            Debug.printError("network is not available", e);
            throw new NetworkException(e);

        }

        if(moveErrorEnum == MoveErrorEnum.NOT_PLAYER_TURN){

            throw new IllegalMoveException(moveErrorEnum);

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

            Debug.printError("network is not available", e);
            throw new NetworkException(e);

        }
    }

    /**
     * This method is used to send chat message to all players in the room
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

    /**
     * this method is used to receive message from the other players
     */
    public void receiveChatMsg(){

        try{

            ReceiveChatPacket packet = (ReceiveChatPacket)inStream.readObject();
            getControllerMain().receiveChatMsg(packet.getNickname(), packet.getMessage());

        }
        catch(IOException | ClassNotFoundException e){

            Debug.printError("network is not available",e);

        }
    }

    /**
     * this method is used to receive moves on tower from other players
     */
    public void receivePlaceOnTower(){

        try {

            ReceivePlaceOnTowerPacket packet = (ReceivePlaceOnTowerPacket)inStream.readObject();
            //TODO method

        }

        catch (ClassNotFoundException | IOException e){


        }

    }

    /**
     * this method is used to receive moves on market from other players
     */
    public void receivePlaceOnMarket(){

        try {

            ReceivePlaceOnMarketPacket packet = (ReceivePlaceOnMarketPacket)inStream.readObject();
            //TODO method

        }

        catch (ClassNotFoundException | IOException e){


        }
    }

    /**
     * this method is used to receive moves on harvest from other players
     */
    public void receiveHarvest(){

        try {

            ReceiveBuildOrHarvestPacket packet = (ReceiveBuildOrHarvestPacket)inStream.readObject();
            //TODO method

        }

        catch (ClassNotFoundException | IOException e){


        }

    }

    /**
     * this method is used to receive moves on build from other players
     */
    public void receiveBuild(){

        try {

            ReceiveBuildOrHarvestPacket packet = (ReceiveBuildOrHarvestPacket)inStream.readObject();
            //TODO method

        }

        catch (ClassNotFoundException | IOException e){


        }

    }

    /**
     * this method is used to receive the end of turn of the other players
     */
    public void receiveEndPhase(){

        try {

            EndPhasePacket packet = (EndPhasePacket)inStream.readObject();
            //TODO method

        }

        catch (ClassNotFoundException | IOException e){

        }
    }

    /**
     * this method is used to receive the new dices loaded by the server
     */
    public void receiveDices(){
        Debug.printVerbose("receiveDices called");
        try{

            DicesPacket packet = (DicesPacket)inStream.readObject();
            Debug.printVerbose("receivedDices packet");
            getControllerMain().receivedDices(packet.getDices());
            Debug.printVerbose("called");
        }
        catch (IOException | ClassNotFoundException e){

            Debug.printError("Error: cannot receive the dices loaded by the server", e);
        }
    }

    /**
     * this method is used to receive the initial board loaded by the server
     */
    public void receiveStartGameBoard(){

        try{

            Board board = (Board)inStream.readObject();
            getControllerMain().receivedStartGameBoard(board);
        }

        catch (IOException | ClassNotFoundException e){

            Debug.printError("Error: cannot receive the initial board", e);
        }
    }

    /**
     * this method is called by the server to inform the client that can start his turn
     */
    public void startTurn(){
        getControllerMain().receivedStartTurnNotification();
    }

    /**
     * this method is called by the server to deliver the new order of players
     */
    public void receiveOrderPlayers(){

        try {
            ArrayList<String> orderPlayers = (ArrayList<String>)inStream.readObject();
            getControllerMain().receivedOrderPlayers(orderPlayers);
        }
        catch (IOException | ClassNotFoundException e){
            Debug.printError("Error: cannot receive the order of the players", e);
        }
    }

    /**
     * this method is called by the server to deliver to the client the nickname used to play the game
     */
    public void receiveClientNickname(){

        try{
            String nickname = (String)inStream.readObject();
            returnNickname(nickname);
        }
        catch (IOException | ClassNotFoundException e){
            Debug.printError("Error: cannot receive the nickname of the client", e);
        }
    }

    /**
     * this method is used to receive the leader cards at the beginning of the game, and the client has to choose one of them
     */
    public void receiveLeaderCards(){

        try{
            LeaderChoicePacket packet = (LeaderChoicePacket)inStream.readObject();
            //TODO method
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("ERROR: client can not receive the leader cards",e);
        }
    }

    /**
     * this method is used by the client to deliver to the server the leader that wants to have
     */
    @Override
    public void deliverLeaderChose(LeaderCard leaderCard) throws NetworkException{

        try{
            outStream.writeObject(PacketType.LEADER_CHOICES);
            outStream.writeObject(new ReceiveLeaderCardChosePacket(leaderCard));
            outStream.flush();
        }
        catch (IOException e){
            Debug.printError("the client cannot deliver the leader he has chosen",e);
            throw new NetworkException(e);
        }
    }

}
