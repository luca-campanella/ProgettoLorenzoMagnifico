package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.leaders.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this is the packet to deliver all the leader cards to choose at the start of the game
 */
public class LeaderChoicePacket implements Serializable{

    ArrayList<LeaderCard> leaderCards;

    public LeaderChoicePacket(ArrayList<LeaderCard> leaderCards){

        this.leaderCards = new ArrayList<>(4);
        this.leaderCards = leaderCards;

    }

    public ArrayList<LeaderCard> getLeaderCards(){
        return leaderCards;
    }
}
