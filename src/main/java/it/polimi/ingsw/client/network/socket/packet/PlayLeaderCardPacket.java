package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;
import java.util.HashMap;

/**
 * packet that contains the name of the leader card
 */
public class PlayLeaderCardPacket implements Serializable {
    /**
     * name of the leader card
     */
    private String nameLeaderCard;

    /**
     * choices done playing the leader card, one of the leader cards has the ability to copy the ability of a leader card already played
     */
    private HashMap<String, String> choicesOnCurrentActionString;

    HashMap<String, Integer> choicesOnCurrentAction;

    /**
     * constructor
     * @param nameLeaderCard
     * @param choicesOnCurrentAction
     */
    public PlayLeaderCardPacket(String nameLeaderCard, HashMap<String, String> choicesOnCurrentActionString,
                                HashMap<String, Integer> choicesOnCurrentAction){
        this.nameLeaderCard=nameLeaderCard;
        this.choicesOnCurrentActionString = choicesOnCurrentActionString;
        this.choicesOnCurrentAction = choicesOnCurrentAction;
    }
    public String getNameCard(){
        return nameLeaderCard;
    }

    public HashMap<String, String> getChoicesOnCurrentActionString() {
        return choicesOnCurrentActionString;
    }

    public HashMap<String, Integer> getChoicesOnCurrentAction() {
        return choicesOnCurrentAction;
    }
}
