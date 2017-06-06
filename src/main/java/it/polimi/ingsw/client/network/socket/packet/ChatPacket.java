package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * this packet is used to deliver the message to the server
 */
public class ChatPacket implements Serializable {
    private String message;
    public ChatPacket(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
