package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

import java.util.HashMap;

/**
 *  the packet created to deliver the action on socket
 */
public class BuildOrHarvest extends MovePacket {

    /**
     * the number of servant used to increase the value of the family member
     */
    private int servantUsed;

    /**
     *  the choices of the player when there are different options on the build card
     */
    private HashMap<String, Integer> playerChoices;

    public BuildOrHarvest(DiceAndFamilyMemberColorEnum familyMemberColor, int servantUsed, HashMap<String, Integer> playerChoices){

        super(familyMemberColor);
        this.servantUsed=servantUsed;
        this.playerChoices = playerChoices;
    }

    public int getServantUsed() {
        return servantUsed;
    }

    public HashMap<String, Integer> getPlayerChoices(){

        return playerChoices;

    }
}
