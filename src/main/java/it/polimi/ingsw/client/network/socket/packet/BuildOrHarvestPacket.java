package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

/**
 * Created by federico on 17/05/2017.
 */
public class BuildOrHarvestPacket extends MovePacket {

    public BuildOrHarvestPacket(DiceAndFamilyMemberColor familyMemberColor, int servantUsed){
        super(familyMemberColor,servantUsed);
    }
}
