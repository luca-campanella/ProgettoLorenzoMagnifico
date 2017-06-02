package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

import java.util.HashMap;

/**
 *  the packet created to deliver the action of place a family member on tower, on socket
 */
public class MoveInTowerPacket extends MovePacket {
    /**
     * number of tower where the family member is moved
     */
    private int numberTower;
    /**
     * floor of the tower where the family member is moved
     */
    private int floorTower;
    /**
     * the choices of the player when has to choose to target of the effect
     */
    private HashMap<String, Integer> playerChoices;

    /**
     * constructor
     */
    public MoveInTowerPacket (DiceAndFamilyMemberColor familyMemberColor, int servantUsed,
                              int numberTower, int floorTower, HashMap<String, Integer> playerChoices){

        super(familyMemberColor, servantUsed);
        this.numberTower=numberTower;
        this. floorTower=floorTower;
        this.playerChoices = playerChoices;

    }

    public int getNumberTower(){

        return numberTower;

    }

    public int getFloorTower(){

        return floorTower;

    }

    public HashMap getPlayersChoices(){

        return playerChoices;

    }
}
