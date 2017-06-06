package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

/**
 *  the packet created to deliver the action on socket
 */
public class MoveBuildOrHarvestPacket extends MovePacket {

    private int servantUsed;

    public MoveBuildOrHarvestPacket(DiceAndFamilyMemberColorEnum familyMemberColor, int servantUsed){

        super(familyMemberColor);
        this.servantUsed=servantUsed;
    }

    public int getServantUsed() {
        return servantUsed;
    }
}
