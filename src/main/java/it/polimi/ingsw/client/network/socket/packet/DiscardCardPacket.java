package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * this packet is send when the player decides to use a leader card
 */
public class DiscardCardPacket implements Serializable {
    /**
     * name of the leader card
     */
    private String nameLeaderCard;
    /**
     * the name of the resource that you want to obtain discarding the leader card
     */
    private String resourceGet;

    /**
     * constructor
     * @param nameLeaderCard
     */
    public DiscardCardPacket(String nameLeaderCard, String resourceGet){
        this.nameLeaderCard=nameLeaderCard;
        this.resourceGet=resourceGet;
    }
    public String getNameCard(){
        return nameLeaderCard;
    }
    public String getResourceGet(){
        return resourceGet;
    }
}
