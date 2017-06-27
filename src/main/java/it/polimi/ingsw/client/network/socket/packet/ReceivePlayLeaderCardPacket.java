package it.polimi.ingsw.client.network.socket.packet;

import java.util.HashMap;

/**
 * this packet is used to deliver the leader card played by a player to the others
 */
public class ReceivePlayLeaderCardPacket extends PlayLeaderCardPacket{

    /**
     * the nickname of the player that ha played the leader card
     */
    private String nickname;

    public ReceivePlayLeaderCardPacket(String nameLeaderCard, HashMap<String, String> choicesOnCurrentActionString, String nickname){
        super(nameLeaderCard,choicesOnCurrentActionString);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
