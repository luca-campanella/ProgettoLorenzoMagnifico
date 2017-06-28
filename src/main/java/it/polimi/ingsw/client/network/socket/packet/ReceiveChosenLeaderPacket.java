package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.leaders.LeaderCard;

/**
 * this class is used to deliver the chosen leader card to the other player
 */
public class ReceiveChosenLeaderPacket extends ChosenLeaderPacket {

    /**
     * this is the nickname of the player that had chose the leader card
     */
    private String nickname;

    public ReceiveChosenLeaderPacket(LeaderCard leaderCard, String nickname){

        super(leaderCard);
        this.nickname = nickname;

    }

    public String getNickname() {
        return nickname;
    }
}
