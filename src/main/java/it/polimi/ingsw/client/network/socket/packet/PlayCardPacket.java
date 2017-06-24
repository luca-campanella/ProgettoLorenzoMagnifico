package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;
import java.util.HashMap;

/**
 * packet that contains the name of the leader card
 */
public class PlayCardPacket implements Serializable {
    /**
     * name of the leader card
     */
    private String nameLeaderCard;

    /**
     * choices done playing the leader card, one of the leader cards has the ability to copy the ability of a leader card already played
     */
    private HashMap<String, String> choicesOnCurrentActionString;

    /**
     * constructor
     * @param nameLeaderCard
     */
    public PlayCardPacket(String nameLeaderCard, HashMap<String, String> choicesOnCurrentActionString){
        this.nameLeaderCard=nameLeaderCard;
        this.choicesOnCurrentActionString = choicesOnCurrentActionString;
    }
    public String getNameCard(){
        return nameLeaderCard;
    }

    public HashMap<String, String> getChoicesOnCurrentActionString() {
        return choicesOnCurrentActionString;
    }
}
