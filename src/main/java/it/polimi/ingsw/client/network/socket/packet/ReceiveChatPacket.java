package it.polimi.ingsw.client.network.socket.packet;

/**
 * thi packet is used to deliver to the client the chat message of the other player (controller -> client)
 */
public class ReceiveChatPacket extends ChatPacket {
    String nickname;

    /**
     * constructor
     * @param nickname nickname of the player that had written the message
     * @param msg the message send
     */
    public ReceiveChatPacket(String nickname,String msg){
        super(msg);
        this.nickname=nickname;
    }
    public String getNickname(){
        return nickname;
    }
}
