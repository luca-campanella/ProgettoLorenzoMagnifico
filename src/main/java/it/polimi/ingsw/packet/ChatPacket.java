package it.polimi.ingsw.packet;

/**
 * this packet is used to deliver the message to the server
 */
public class ChatPacket {
    private String message;
    public ChatPacket(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
