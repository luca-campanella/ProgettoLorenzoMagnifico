package it.polimi.ingsw.client.network.socket.packet;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.HashMap;

/**
 * this packet is used to deliver to the clients when a player discrds a leader card
 */
public class ReceiveDiscardLeaderCardPacket extends DiscardLeaderCardPacket {

    /**
     * the nickname of the player that had discarded the leader card
     */
    private java.lang.String nickname;

    public ReceiveDiscardLeaderCardPacket(java.lang.String nickname, java.lang.String nameLeaderCard, HashMap<java.lang.String
            , Integer> resourceChoose){

        super(nameLeaderCard, resourceChoose);
        this.nickname = nickname;
    }

    public java.lang.String getNickname() {
        return nickname;
    }
}
