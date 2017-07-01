package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * this class tests harvest action space
 */
public class HarvestASTest {
    HarvestAS harvest = new HarvestAS();
    @Test
    public void checkIfFirst() throws Exception {
        harvest.setValueMalus(3);
        assertEquals(true, harvest.checkIfFirst());
        assertEquals(0, harvest.getValueNeeded(true));
        harvest.addFamilyMember(new FamilyMember(new Dice(DiceAndFamilyMemberColorEnum.BLACK), new Player()));
        assertEquals(3, harvest.getValueNeeded(true));
        assertEquals(1,harvest.getOccupyingFamilyMemberNumber());
        harvest.addFamilyMember(new FamilyMember(new Dice(DiceAndFamilyMemberColorEnum.ORANGE), new Player()));
        assertEquals(2,harvest.getOccupyingFamilyMemberNumber());
        assertEquals(10, harvest.getValueNeeded(false));


    }

    @Test
    public void setValueStandard() throws Exception {
        harvest.setValueStandard(2);
        assertEquals(2, harvest.getValueStandard());
    }

    @Test
    public void setTwoPlayersOneSpace() throws Exception {
        harvest.setTwoPlayersOneSpace(true);
        assertEquals(true, harvest.isTwoPlayersOneSpace());
    }

}