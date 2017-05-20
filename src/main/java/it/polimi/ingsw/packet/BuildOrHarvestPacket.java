package it.polimi.ingsw.packet;

import it.polimi.ingsw.gamelogic.Player.FamilyMemberColor;

import java.io.Serializable;

/**
 * Created by federico on 17/05/2017.
 */
public class BuildOrHarvestPacket extends MovePacket {

    public BuildOrHarvestPacket(FamilyMemberColor familyMemberColor, int servantUsed){
        super(familyMemberColor,servantUsed);
    }
}
