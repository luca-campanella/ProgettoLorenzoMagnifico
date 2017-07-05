package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * this class is a wrapper used to contains the name of the player, his points and his position at the end of the game
 */
public class PlayerPositionEndGamePacket implements Serializable{

    /**
     * the nickname of the player
     */
    private String nickname;

    /**
     * the position at the ed of the game
     */
    private int position;

    /**
     * the victory points owned at the end of the game
     */
    private int victoryPoints;

    public PlayerPositionEndGamePacket(String nickname, int position, int victoryPoints){

        this.nickname = nickname;
        this.position = position;
        this.victoryPoints = victoryPoints;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPosition() {
        return position;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
