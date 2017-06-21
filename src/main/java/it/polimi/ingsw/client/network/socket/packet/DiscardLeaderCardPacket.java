package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;
import java.util.HashMap;

/**
 * this packet is send when the player decides to use a leader card
 */
public class DiscardLeaderCardPacket implements Serializable {
    /**
     * name of the leader card
     */
    private String nameLeaderCard;
    /**
     * the name of the resource that you want to obtain discarding the leader card
     */
    private HashMap<String, Integer> resourceChoose;

    /**
     * constructor
     * @param nameLeaderCard
     */
    public DiscardLeaderCardPacket(String nameLeaderCard, HashMap<String, Integer> resourceChoose){
        this.nameLeaderCard=nameLeaderCard;
        this.resourceChoose=resourceChoose;
    }
    public String getNameCard(){
        return nameLeaderCard;
    }
    public HashMap<String, Integer> getResourceGet(){
        return resourceChoose;
    }
}
