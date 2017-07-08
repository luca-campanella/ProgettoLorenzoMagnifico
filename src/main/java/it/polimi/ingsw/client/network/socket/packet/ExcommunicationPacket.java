package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this packet is used to deliver the players excommunicated
 */
public class ExcommunicationPacket implements Serializable{

    /**
     * the players excommunicated
     */
    private ArrayList<String> nicknamePlayersExcommunicated;

    /**
     * the number of tile to give to the excommunicated players
     */
    private int numTile;

    public ExcommunicationPacket(ArrayList<String> nicknamePlayersExcommunicated, int numTile){

        this.nicknamePlayersExcommunicated = new ArrayList<>(nicknamePlayersExcommunicated);
        this.numTile = numTile;
    }

    public ArrayList<String> getNicknamePlayersExcommunicated() {
        return nicknamePlayersExcommunicated;
    }

    public int getNumTile() {
        return numTile;
    }
}
