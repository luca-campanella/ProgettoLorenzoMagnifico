package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * packet that contains the name of the leader card
 */
public class PlayCardPacket implements Serializable {
    /**
     * name of the leader card
     */
    private String nameLeaderCard;

    /**
     * constructor
     * @param nameLeaderCard
     */
    public PlayCardPacket(String nameLeaderCard){
        this.nameLeaderCard=nameLeaderCard;
    }
    public String getNameCard(){
        return nameLeaderCard;
    }
}
