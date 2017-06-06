package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

/**
 * the packet created to deliver the action of harvest on socket
 */
public class HarvestPacket extends MoveBuildOrHarvestPacket {

    public HarvestPacket(DiceAndFamilyMemberColorEnum familyMemberColor, int servantUsed){
        super(familyMemberColor,servantUsed);
    }
}
