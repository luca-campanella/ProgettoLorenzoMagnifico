package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.FamilyMember;

/**
 * the packet created to deliver the action of harvest on socket
 */
public class HarvestPacket extends MoveBuildOrHarvestPacket {

    public HarvestPacket(FamilyMember familyMember, int servantUsed){
        super(familyMember,servantUsed);
    }
}
