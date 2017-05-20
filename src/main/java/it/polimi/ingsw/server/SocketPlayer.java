package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.LoginErrorEnum;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.packet.LoginOrRegisterPacket;
import it.polimi.ingsw.packet.PacketType;
import it.polimi.ingsw.packet.PlayCardPacket;
import it.polimi.ingsw.packet.RegisterErrorEnum;
import it.polimi.ingsw.protocol.ReadClientPacketProtocol;
import it.polimi.ingsw.utils.Debug;

import java.io.*;
import java.net.Socket;

public class SocketPlayer extends AbstractConnectionPlayer implements Runnable {

    private Socket socket;

    private ObjectInputStream inStream;

    private ObjectOutputStream outStream;

    private ServerMain serverMainInst;

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
                //TODO tell clients the packet needs to be a login or a register one
            } while (packetType != PacketType.LOGIN && packetType != PacketType.REGISTER);
            //readPacket.doMethod(packetType);
        } catch (IOException | ClassNotFoundException e) {
            Debug.printError("Something went wrong when reading objects from client with address " + socket.getInetAddress(), e);
            //  closeEverything(); //At this point the only thing we can do is close the connection and terminate the process
            //TODO signal room that one player is no longer connected
        }
        while(true){
            try{
                packetType= (PacketType)inStream.readObject();
                //readPacket.doMethod(packetType);
            }
            catch(IOException | ClassNotFoundException e){
                Debug.printError("Network is not working",e);
            }
    }}


    public void registerPlayer() {

        try {

            LoginOrRegisterPacket packet = (LoginOrRegisterPacket) inStream.readObject();
            serverMainInst.registerPlayer(packet.getNickname(), packet.getPassword());
        }
        catch (ClassNotFoundException e) {
            Debug.printError("Network is not working", e);
        }
        catch (UsernameAlreadyInUseException e) {
            try {
                outStream.writeObject(RegisterErrorEnum.ALREADY_EXISTING_USERNAME);
            } catch (IOException c) {
                Debug.printError("Network is not working", c);
            }
        } catch (IOException c) {
            Debug.printError("Network is not working", c);
        }
    }



    public void loginPlayer(){
        try {
            //the exceptions are caught by the client
            LoginOrRegisterPacket packet = (LoginOrRegisterPacket) inStream.readObject();
            serverMainInst.loginPlayer(packet.getNickname(), packet.getPassword());
        }
        catch(ClassNotFoundException e){
            Debug.printError("Network is not working",e);}
        catch (LoginException e) {
            try{
            outStream.writeObject(e.getErrorType());
        }
        catch (IOException e1){
            Debug.printError("Network is not working",e1);}
        }
    }
    public void playCard() throws NetworkException{
        try{
            PlayCardPacket packet=(PlayCardPacket)inStream.readObject();
            //TODO method
        }
        catch(IOException e){
            throw  new NetworkException(e);
        }
        catch(ClassNotFoundException e) {
            //TODO handle exception
            e.printStackTrace();
        }
    }


    /**
     * This method is called by the room to send a chat message arrived from another client. (Direction: server -> client)
     * @param msg
     * @throws NetworkException
     */
    //@Override
    public void floodChatMsg(String msg) throws NetworkException {
        //TODO implement
    }

    /*public void playCard(){
       try{
            PlayCardPacket card=(PlayCardPacket)inStream.readObject();

    }*/
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
}

