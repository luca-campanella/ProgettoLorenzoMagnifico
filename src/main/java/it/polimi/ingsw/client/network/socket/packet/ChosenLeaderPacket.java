package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.leaders.LeaderCard;

import java.io.Serializable;

/**
 * this is the packet to deliver the leader card chose by the client
 */
public class ChosenLeaderPacket implements Serializable{

    private LeaderCard leaderCard;

    public ChosenLeaderPacket(LeaderCard leaderCard){
        this.leaderCard = leaderCard;
    }

    public LeaderCard getLeaderCard(){
        return leaderCard;
    }
}
