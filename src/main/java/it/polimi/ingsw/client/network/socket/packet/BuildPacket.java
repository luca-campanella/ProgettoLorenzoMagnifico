package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.FamilyMember;

import java.util.HashMap;

/**
 * the packet created to deliver the action of build on socket
 */
public class BuildPacket  extends MoveBuildOrHarvestPacket {

    /**
     *  the choices of the player when there are different options on the build card
     */
    private HashMap<String, Integer> playerChoices;

    public BuildPacket(FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices){

        super(familyMember,servantUsed);
        this.playerChoices = playerChoices;

    }

    public HashMap getPlayerChoices(){

        return playerChoices;

    }
}
