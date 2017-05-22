package it.polimi.ingsw.packet;

import it.polimi.ingsw.gamelogic.Player.DiceAndFamilyMemberColor;

/**
 * Created by federico on 17/05/2017.
 */
public class BuildOrHarvestPacket extends MovePacket {

    public BuildOrHarvestPacket(DiceAndFamilyMemberColor familyMemberColor, int servantUsed){
        super(familyMemberColor,servantUsed);
    }
}
