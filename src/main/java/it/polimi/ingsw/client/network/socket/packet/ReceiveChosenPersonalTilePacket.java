package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.PersonalTile;

import java.io.Serializable;

/**
 * this class is used like a packet to deliver the personal tile chosen by a player using socket
 */
public class ReceiveChosenPersonalTilePacket implements Serializable{

    /**
     * the nickname of the player that had chosen the personal tile
     */
    private String nickname;

    /**
     * the personal tile chosen by the player
     */
    private PersonalTile personalTile;

    public ReceiveChosenPersonalTilePacket(String nickname, PersonalTile personalTile){
        this.nickname = nickname;
        this.personalTile = personalTile;
    }

    public PersonalTile getPersonalTile() {
        return personalTile;
    }

    public String getNickname() {
        return nickname;
    }
}
