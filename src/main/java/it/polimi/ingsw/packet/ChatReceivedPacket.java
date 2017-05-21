package it.polimi.ingsw.packet;

/**
 * this packet is used to deliver to the client the chat of the other clients
 */
public class ChatReceivedPacket extends ChatPacket{

    /*
     * the nickname of the player that had send the message
     */
    private String senderNickname;

    public ChatReceivedPacket(String senderNickname, String msg){

        super(msg);
        this.senderNickname=senderNickname;
    }
    public String getSenderNickname(){
        return senderNickname;
    }
}
