package it.polimi.ingsw.client.network.socket.packet;

import java.util.HashMap;

/**
 * this packet is used to deliver to the clients when a player discrds a leader card
 */
public class ReceiveDiscardOrActivatedLeaderCardPacket extends DiscardAndActivateLeaderCardPacket {

    /**
     * the nickname of the player that had discarded the leader card
     */
    private java.lang.String nickname;

    public ReceiveDiscardOrActivatedLeaderCardPacket(java.lang.String nickname, java.lang.String nameLeaderCard, HashMap<java.lang.String
            , Integer> resourceChoose){

        super(nameLeaderCard, resourceChoose);
        this.nickname = nickname;
    }

    public java.lang.String getNickname() {
        return nickname;
    }
}
