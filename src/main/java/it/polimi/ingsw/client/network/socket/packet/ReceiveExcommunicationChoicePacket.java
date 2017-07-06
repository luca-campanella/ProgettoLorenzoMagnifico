package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * this packet is used to deliver to the different players the choice done on the excommunication by another player
 */
public class ReceiveExcommunicationChoicePacket implements Serializable{

    /**
     * the nickname of the player that had done the choice
     */
    private String nickname;

    /**
     * the response on the excommunication of the player:
     * -"yes": if he wants to avoid the excommunication;
     * -"no": if he doesn't want to avoid the excommunication;
     */
    private String response;

    /**
     * the number of the tile to give to the player if he is excommunicated
     */
    private int numTile;

    public ReceiveExcommunicationChoicePacket(String nickname, String response, int numTile){

        this.nickname = nickname;
        this.response = response;
        this.numTile = numTile;
    }

    public int getNumTile() {
        return numTile;
    }

    public String getNickname() {
        return nickname;
    }

    public String getResponse() {
        return response;
    }
}
