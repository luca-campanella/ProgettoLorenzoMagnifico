package it.polimi.ingsw.packet;

/**
 * Created by federico on 16/05/2017.
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
