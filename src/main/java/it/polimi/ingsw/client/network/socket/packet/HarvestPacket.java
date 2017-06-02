package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

/**
 * the packet created to deliver the action of harvest on socket
 */
public class HarvestPacket extends MovePacket {

    public HarvestPacket(DiceAndFamilyMemberColor familyMemberColor, int servantUsed){
        super(familyMemberColor,servantUsed);
    }
}
