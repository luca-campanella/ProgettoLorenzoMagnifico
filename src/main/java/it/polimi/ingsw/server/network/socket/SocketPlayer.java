package it.polimi.ingsw.server.network.socket;

import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.client.exceptions.*;
import it.polimi.ingsw.client.network.socket.packet.*;
import it.polimi.ingsw.server.network.socket.protocol.ReadClientPacketProtocol;
import it.polimi.ingsw.utils.Debug;

import java.io.*;
import java.net.Socket;
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
        Debug.printVerbose("creazione  player");
        outStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        outStream.flush();

        InputStream instream1 = socket.getInputStream();
        Debug.printVerbose("Input stream from socket got");
        BufferedInputStream bufferesdinstream = new BufferedInputStream(instream1);
        Debug.printVerbose("Buffered input stream created");
        try {
            inStream = new ObjectInputStream(bufferesdinstream);
        } catch(IOException e) {
            Debug.printError("Error creating the ObjStream", e);
            throw e;
        }
        Debug.printVerbose("inStream created");

        Debug.printVerbose("creazione  player");
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
            }
        }
    }


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

    public void moveInTower(){
        try{
            MoveInTowerPacket packet=(MoveInTowerPacket)inStream.readObject();
            //TODO method in room
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

    public void moveInMarket(){
        try{
            MoveInMarketPacket packet=(MoveInMarketPacket)inStream.readObject();
            //TODO method in room
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

    public void harvesting(){
        try{
            HarvestPacket packet=(HarvestPacket) inStream.readObject();
            //TODO method
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not working", e);
        }

    }

    public void building(){
        try{
            BuildPacket packet=(BuildPacket) inStream.readObject();
            //TODO method
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("network is not working", e);
        }

    }

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
           getRoomContr().floodChatMsg(this,msg);
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
            outStream.writeObject(new MoveInTowerPacket(familyMember,towerIndex,floorIndex, playerChoices));
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
            outStream.writeObject(new MoveInMarketPacket(familyMember, marketIndex));
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

            outStream.writeObject(PacketType.BUILDING);
            outStream.writeObject(new BuildPacket(familyMember,servant,playerChoices));
            outStream.flush();

        }

        catch (IOException e){
            Debug.printError("network is not avaiable", e);
            throw new NetworkException(e);

        }
    }

    /**
     * This method is called by the server to send a packet with the information of the move. (Direction: server -> client)
     */
    @Override
    public void receiveHarvest(FamilyMember familyMember, int servant) throws NetworkException {

        try{

            outStream.writeObject(PacketType.HARVESTING);
            outStream.writeObject(new HarvestPacket(familyMember,servant));
            outStream.flush();

        }
        catch (IOException e){

            Debug.printError("network is not available", e);
            throw new NetworkException(e);

        }

    }

    public void endPhase(){
        //TODO call the room' s method to tell the player had ended his phase
    }
}

