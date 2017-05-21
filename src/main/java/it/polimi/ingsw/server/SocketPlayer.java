package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.packet.*;
import it.polimi.ingsw.protocol.ReadClientPacketProtocol;
import it.polimi.ingsw.utils.Debug;

import java.io.*;
import java.net.Socket;

public class SocketPlayer extends AbstractConnectionPlayer implements Runnable {

    private Socket socket;

    private ObjectInputStream inStream;

    private ObjectOutputStream outStream;

    private ServerMain serverMainInst;

    /**
     * the type of packet that can be received, it works like a header for the input object
     */
    private PacketType packetType;

    /**
     * the protocol used to read the packet of the client
     */
    private ReadClientPacketProtocol readPacket;

    /**
     * constructor to open the streams
     *
     * @param socket
     * @param serverMainInst needs this to call login and register functions and to be able to join a room
     * @throws IOException
     */
    public SocketPlayer(Socket socket, ServerMain serverMainInst) throws IOException {

        this.socket = socket;
        this.serverMainInst = serverMainInst;
        inStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        outStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        readPacket = new ReadClientPacketProtocol(this);

    }

    public void run() {
        Debug.printVerbose("New socket player object waiting for login");
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
                packetType= (PacketType)inStream.readObject();
                readPacket.doMethod(packetType);
            }
            catch(IOException | ClassNotFoundException e){
                Debug.printError("Network is not working",e);
                closeEverything();
            }
        }
    }


    public void registerPlayer() {

        try {

            LoginOrRegisterPacket packet = (LoginOrRegisterPacket) inStream.readObject();
            serverMainInst.registerPlayer(packet.getNickname(), packet.getPassword());
        }
        catch (UsernameAlreadyInUseException e) {
            try {
                outStream.writeObject(RegisterErrorEnum.ALREADY_EXISTING_USERNAME);
                outStream.flush();
            } catch (IOException c) {
                Debug.printError("Network is not working", c);
            }
        } catch (IOException | ClassNotFoundException c) {
            Debug.printError("Network is not working", c);
        }
    }



    public void loginPlayer(){
        try {
            LoginOrRegisterPacket packet = (LoginOrRegisterPacket) inStream.readObject();
            serverMainInst.loginPlayer(packet.getNickname(), packet.getPassword());
        }

        catch (LoginException e) {
            try{
            outStream.writeObject(e.getErrorType());
            outStream.flush();
            }
            catch (IOException e1){
                Debug.printError("Network is not working",e1);
            }
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("Network is not working",e);}
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
                Debug.printError("Network is not working", e1);
            }
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("Network is not working", e);
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
                Debug.printError("Network is not working", e1);
            }
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("Network is not working", e);
        }

    }

    public void harvesting(){
        try{
            BuildOrHarvestPacket packet=(BuildOrHarvestPacket) inStream.readObject();
            //TODO method
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("Network is not working", e);
        }

    }

    public void building(){
        try{
            BuildOrHarvestPacket packet=(BuildOrHarvestPacket) inStream.readObject();
            //TODO method
        }
        catch(IOException | ClassNotFoundException e){
            Debug.printError("Network is not working", e);
        }

    }

    public void playCard(){
        try{
            PlayCardPacket packet=(PlayCardPacket)inStream.readObject();
            //TODO method
        }
        catch(IOException e){
            Debug.printError("Network is not working", e);
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
            Debug.printError("Network is not working", e);
        }
        catch(ClassNotFoundException e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }


    /**
     * This method is called by the client to send a chat message to the others client. (Direction: client -> server)
     * @throws NetworkException
     */
    public void floodChatMsg(){
       try {
           String msg = (String) inStream.readObject();
           getRoomContr().floodChatMsg(this,msg);
       }
       catch(IOException | ClassNotFoundException e){
           Debug.printError("Network is not working",e);
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

    @Override
    public void receiveChatMsg(String senderNickname, String msg) throws NetworkException {
        //TODO
        ;
    }
    public void endPhase(){
        //TODO call the room' s method to tell the player had ended his phase
    }
}

