package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

import java.util.HashMap;

/**
 *  the packet created to deliver the action of place a family member on tower, on socket
 */
public class PlaceOnTowerPacket extends MovePacket {
    /**
     * number of tower where the family member is moved
     */
    private int towerIndex;
    /**
     * floor of the tower where the family member is moved
     */
    private int floorIndex;
    /**
     * the choices of the player when has to choose to target of the effect
     */
    private HashMap<String, Integer> playerChoices;

    /**
     * constructor
     */
    public PlaceOnTowerPacket(DiceAndFamilyMemberColorEnum familyMemberColor,
                              int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices){

        super(familyMemberColor);
        this.towerIndex =towerIndex;
        this.floorIndex =floorIndex;
        this.playerChoices = playerChoices;

    }

    public int getTowerIndex(){

        return towerIndex;

    }

    public int getFloorIndex(){

        return floorIndex;

    }

    public HashMap getPlayersChoices(){

        return playerChoices;

    }
}
