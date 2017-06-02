package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.FamilyMember;

import java.io.Serializable;

/**
 *  the packet created to deliver the action on socket
 */
public class MoveBuildOrHarvestPacket extends MovePacket {

    private int servantUsed;

    public MoveBuildOrHarvestPacket(FamilyMember familyMember, int servantUsed){

        super(familyMember);
        this.servantUsed=servantUsed;
    }

    public int getServantUsed() {
        return servantUsed;
    }
}
