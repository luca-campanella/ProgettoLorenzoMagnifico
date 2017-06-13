package it.polimi.ingsw.server.network.socket;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.client.exceptions.*;
import it.polimi.ingsw.client.network.socket.packet.*;
import it.polimi.ingsw.server.network.socket.protocol.ReadClientPacketProtocol;
import it.polimi.ingsw.utils.Debug;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class SocketPlayer extends AbstractConnectionPlayer implements Runnable {

    private Socket socket;

    private ObjectInputStream inStream;

    private ObjectOutputStream outStream;

    private ServerMain serverMainInst;

    /**
     * the protocol used to read the packet of the client
     */
    private ReadClientPacketProtocol readPacket;

    /**
     * constructor to open the streams
     * @param serverMainInst needs this to call login and register functions and to be able to join a room
     */
    public SocketPlayer(Socket socket, ServerMain serverMainInst) throws IOException {

        this.socket = socket;
        this.serverMainInst = serverMainInst;
        Debug.printVerbose("creation  player");
        outStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        outStream.flush();

        InputStream instream1 = socket.getInputStream();
        Debug.printVerbose("Input stream from socket got");
        BufferedInputStream bufferedInstream = new BufferedInputStream(instream1);
        Debug.printVerbose("Buffered input stream created");
        try {

            inStream = new ObjectInputStream(bufferedInstream);

        }

        catch(IOException e) {

            Debug.printError("Error creating the ObjStream", e);
            throw e;

        }

        Debug.printVerbose("inStream created");
        Debug.printVerbose("creation  player");
        readPacket = new ReadClientPacketProtocol(this);

    }

    public void run() {
        Debug.printVerbose("New socket player object waiting for login");

        /**
         *the type of packet that can be received, it works like a header for the input object
         */
        PacketType packetType;
        try {
            do {

                packetType = (PacketType) inStream.readObject();

            } while (packetType != PacketType.LOGIN && packetType != PacketType.REGISTER);
            readPacket.doMethod(packetType);
        } catch (IOException | ClassNotFoundException e) {
            Debug.printError("Something went wrong when reading objects from client with address " + socket.getInetAddress(), e);
            closeEverything(); //At this point the only thing we can do is close the connection and terminate the process
            //TODO signal room that one player is no longer connected
        }
        while(true){
            try{

                packetType = (PacketType)inStream.readObject();
                readPacket.doMethod(packetType);

            }
            catch(IOException | ClassNotFoundException e){
                Debug.printError("network is not working",e);
                closeEverything();
                break;
            }
        }
    }

    /**
     * the registration on the player on the game, it put the infotmation on the database and inserts the player on the available room
     */
    public void registerPlayer() {

        try {

            LoginOrRegisterPacket packet = (LoginOrRegisterPacket) inStream.readObject();
            serverMainInst.registerPlayer(packet.getNickname(), packet.getPassword());
            setNickname(packet.getNickname());
            serverMainInst.makeJoinRoomLogin(this);
            outStream.writeObject(LoginErrorEnum.NO_ERROR);
            outStream.flush();
        }
        catch (UsernameAlreadyInUseException e) {
            try {
                outStream.writeObject(RegisterErrorEnum.ALREADY_EXISTING_USERNAME);
                outStream.flush();
            } catch (IOException c) {
                Debug.printError("network is not working", c);
            }
        } catch (IOException | ClassNotFoundException c) {
            Debug.printError("network is not working", c);
        }
    }

    /**
     * the login of the player on the game, it controls the information and, if the control is successful, inserts the player on the available room
     */
    public void loginPlayer(){

        try {
            LoginOrRegisterPacket packet = (LoginOrRegisterPacket) inStream.readObject();
            serverMainInst.loginPlayer(packet.getNickname(), packet.getPassword());
            setNickname(packet.getNickname());
            serverMainInst.makeJoinRoomLogin(this);
            outStream.writeObject(LoginErrorEnum.NO_ERROR);
            outStream.flush();
        }

        catch (LoginException e) {
            try{
            outStream.writeObject(e.getErrorType());
            outStream.flush();
            }
            catch (IOException e1){
                Debug.printError("network is not working",e1);
            }
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not working",e);}
    }

    /**
     * receives the packet from socket of the move on tower and deliver the move to the room
     */
    public void placeOnTower(){

        try{

            PlaceOnTowerPacket packet=(PlaceOnTowerPacket)inStream.readObject();
            getRoom().placeOnTower(getFamilyMemberByColor(packet.getFamilyMemberColor()), packet.getTowerIndex(), packet.getFloorIndex(), packet.getPlayersChoices());
            outStream.writeObject(MoveErrorEnum.NO_ERROR);

        }
        catch (IllegalMoveException e){
            try{
                outStream.writeObject(e.getErrorType());
            }
            catch (IOException e1){
                Debug.printError("network is not working", e1);
            }
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not working", e);
        }

    }

    /**
     * receives the packet from socket of the move on market and deliver the move to the room
     */
    public void placeOnMarket(){
        try{

            PlaceOnMarketPacket packet=(PlaceOnMarketPacket)inStream.readObject();
            getRoom().placeOnMarket(getFamilyMemberByColor(packet.getFamilyMemberColor()), packet.getMarketIndex(), packet.getPlayerChoices());
            outStream.writeObject(MoveErrorEnum.NO_ERROR);

    }
        catch (IllegalMoveException e){
            try{
                outStream.writeObject(e.getErrorType());
            }
            catch (IOException e1){
                Debug.printError("network is not working", e1);
            }
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not working", e);
        }

    }

    /**
     * receives the packet from socket of the move of harvesting and deliver the move to the room
     */
    public void harvest(){
        try{
            BuildOrHarvest packet=(BuildOrHarvest) inStream.readObject();
            getRoom().harvest(getFamilyMemberByColor(packet.getFamilyMemberColor()), packet.getServantUsed(),packet.getPlayerChoices());
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not working", e);
        }

    }

    public void build(){
        try{
            BuildOrHarvest packet=(BuildOrHarvest) inStream.readObject();
            getRoom().build(getFamilyMemberByColor(packet.getFamilyMemberColor()), packet.getServantUsed(), packet.getPlayerChoices());
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not working", e);
        }

    }

    /**
     * receives the packet from socket of the playing of a leader card and deliver the move to the room
     */
    public void playCard(){
        try{
            PlayCardPacket packet=(PlayCardPacket)inStream.readObject();
            //TODO method
        }
        catch(IOException e){
            Debug.printError("network is not working", e);
        }
        catch(ClassNotFoundException e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }

    /**
     * receives the packet from socket of the discarding of a leader card and deliver the move to the room
     */
    public void discardCard(){
        try{
            DiscardCardPacket packet=(DiscardCardPacket)inStream.readObject();
            //TODO method
        }
        catch(IOException e){
            Debug.printError("network is not working", e);
        }
        catch(ClassNotFoundException e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }


    /**
     * This method is called by the client to send a chat message to the others client. (Direction: client -> server)
     */
    public void floodChatMsg(){
       try {
           String msg = (String) inStream.readObject();
           System.out.println("read the object");
           getRoom().floodChatMsg(this,msg);
           System.out.println("flood the object");
       }
       catch(IOException | ClassNotFoundException e){
           Debug.printError("network is not working",e);
       }
    }

    private void closeEverything()
    {
        try {
            inStream.close();
            outStream.close();
            socket.close();
        } catch (IOException e) {
            Debug.printError("Can't close the socket connection with client", e);
        }
    }

    /**
     * This method is called by the server to send a chat message to the client. (Direction: server -> client)
     */
    @Override
    public void receiveChatMsg(String senderNickname, String msg) throws NetworkException {

        ReceiveChatPacket chatPacket= new ReceiveChatPacket(senderNickname , msg);
        try{
            outStream.writeObject(PacketType.CHAT);
            outStream.writeObject(chatPacket);
            outStream.flush();
        }
        catch(IOException e){

            Debug.printError("ERROR: the player " + senderNickname + " had tried to write a message in the chat", e);
        }
    }

    /**
     * This method is called by the server to send a packet with the information of the move. (Direction: server -> client)
     */
    public void receivePlaceOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws NetworkException{

        try{

            outStream.writeObject(PacketType.MOVE_IN_TOWER);
            outStream.writeObject(new ReceivePlaceOnTowerPacket(familyMember.getPlayer().getNickname(),familyMember.getColor(),towerIndex,floorIndex, playerChoices));
            outStream.flush();

        }
        catch (IOException  e){

            Debug.printError("Connection not available",e);
            throw new NetworkException(e);

        }

    }

    /**
     * This method is called by the server to send a packet with the information of the move. (Direction: server -> client)
     */
    @Override
    public void receivePlaceOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws NetworkException {

        try{

            outStream.writeObject(PacketType.MOVE_IN_MARKET);
            outStream.writeObject(new ReceivePlaceOnMarketPacket(familyMember.getPlayer().getNickname(),familyMember.getColor(), marketIndex, playerChoices));
            outStream.flush();

        }

        catch(IOException e){

            Debug.printError("network is not available",e);
            throw new NetworkException(e);

        }

    }

    /**
     * This method is called by the server to send a packet with the information of the move. (Direction: server -> client)
     */
    @Override
    public void receiveBuild(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws NetworkException {

        try{

            outStream.writeObject(PacketType.BUILD);
            outStream.writeObject(new ReceiveBuildOrHarvestPacket(familyMember.getPlayer().getNickname(),familyMember.getColor(),servant,playerChoices));
            outStream.flush();

        }

        catch (IOException e){
            Debug.printError("network is not available", e);
            throw new NetworkException(e);

        }
    }

    /**
     * This method is called by the server to send a packet with the information of the move. (Direction: server -> client)
     */
    @Override
    public void receiveHarvest(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws NetworkException {

        try{

            outStream.writeObject(PacketType.HARVEST);
            outStream.writeObject(new ReceiveBuildOrHarvestPacket(familyMember.getPlayer().getNickname(),familyMember.getColor(),servant, playerChoices));
            outStream.flush();

        }
        catch (IOException e){

            Debug.printError("network is not available", e);
            throw new NetworkException(e);

        }

    }

    /**
     * deliver to the room the ending of a player's phase
     */
    public void endPhase(){

        try {
            getRoom().endPhase(this);
        }

        catch (IllegalMoveException e){

        }

    }

    /**
     * information delivered by the room to the other players, inform the player that a player had ended his phase
     * @throws NetworkException
     */
    public void receiveEndPhase(AbstractConnectionPlayer player) throws NetworkException{

        try{

            outStream.writeObject(PacketType.END_PHASE);
            outStream.writeObject(new EndPhasePacket(player.getNickname()));
            outStream.flush();

        }
        catch (IOException e){

            Debug.printError("network is not available", e);
            throw new NetworkException(e);

        }

    }

    /**
     * the method to deliver to the client the new dices on the board
     * @throws NetworkException
     */
    @Override
    public void receiveDices(ArrayList<Dice> dices) throws NetworkException {

        try{

            outStream.writeObject(PacketType.DICE);
            outStream.writeObject(new DicesPacket(dices));
            outStream.flush();

        }
        catch (IOException e){

            Debug.printError("network is not available", e);
            throw new NetworkException(e);

        }
    }

    /**
     * deliver to the socket client the initial board
     * @throws NetworkException if the connection goes wrong
     */
    @Override
    public void receiveStartGameBoard(Board gameBoard) throws NetworkException{

        try{

            outStream.writeObject(PacketType.GAME_BOARD);
            outStream.writeObject(gameBoard);
            outStream.flush();

        }

        catch (IOException e){

            Debug.printError("Cannot deliver the board to the server", e);
            throw new NetworkException(e);
        }

    }

    /**
     * this method is used to inform the player that his turn is started
     * @throws NetworkException if the connection goes wrong
     */
    @Override
    public void receiveStartOfTurn() throws NetworkException{

        try{
            outStream.writeObject(PacketType.START_TURN);
            outStream.flush();
        }
        catch (IOException e){
            Debug.printError("Cannot deliver the token to start the turn");
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used to deliver the players in order of turn
     * @throws NetworkException if the connection goes wrong
     */
    @Override
    public void deliverOrderPlayers(ArrayList<String> orderPlayers) throws NetworkException{

        try{
            outStream.writeObject(PacketType.ORDER_PLAYERS);
            outStream.writeObject(orderPlayers);
            outStream.flush();
        }
        catch (IOException e){
            Debug.printError("Cannot deliver the order of the players");
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used to deliver the leader card to the player (server -> client)
     * @param cardToPlayer card that the player receives
     * @throws NetworkException if the connection goes wrong
     */
    @Override
    public void receiveLeaderCards(ArrayList<LeaderCard> cardToPlayer) throws NetworkException {

        try{
            outStream.writeObject(PacketType.LEADER_CHOICES);
            outStream.writeObject(new LeaderChoicePacket(cardToPlayer));
            outStream.flush();
        }
        catch (IOException e) {
            Debug.printError("Cannot deliver the choice of the leader cards to the players");
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used to deliver the leader card to the room (client -> server)
     */
    public void deliverLeaderCards(){

        try{
            LeaderChoicePacket packet = (LeaderChoicePacket)inStream.readObject();
            getRoom().receiveLeaderCards(packet.getLeaderCards(), this);
        }
        catch (IOException | ClassNotFoundException e){
            Debug.printError("ERROR: cannot receive the leader cards from " + getNickname());
        }
    }

    /**
     * this method is used to deliver the nickname of the player to the client
     */
    private void deliverNicknamePlayer(String nickname){

        try{
            outStream.writeObject(PacketType.NICKNAME);
            outStream.writeObject(nickname);
            outStream.flush();
        }

        catch (IOException e){
            Debug.printError("Cannot deliver the nickname of the player");
        }
    }

}

