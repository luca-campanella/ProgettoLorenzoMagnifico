package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests reductionOnDiceEffect
 */
public class ReductionOnDiceEffectTest {
    JSONLoader jsonLoader;
    ArrayList<ExcommunicationTile> excommunicationTiles;
    AbstractExcommunicationTileEffect cardEffect;
    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        //setting up test for our specific card
        jsonLoader = new JSONLoader();
        excommunicationTiles = jsonLoader.loadExcommunicationTiles();
        //setting up test for the specific method
        ArrayList<DiceAndFamilyMemberColorEnum> colors = new ArrayList<>();
        colors.add(DiceAndFamilyMemberColorEnum.BLACK);
        cardEffect = new ReductionOnDiceEffect(colors, 1);
    }


    @Test
    public void reductionOnDice() throws Exception {
        int valueTest;
        //those 2 lines tests the card loaded
        valueTest = excommunicationTiles.get(7).effect.reductionOnDice(DiceAndFamilyMemberColorEnum.WHITE);
        int valueSecondTest = excommunicationTiles.get(7).effect.reductionOnDice(DiceAndFamilyMemberColorEnum.NEUTRAL);
        //those 2 tests the effect itself
        int valueThirdTest = cardEffect.reductionOnDice(DiceAndFamilyMemberColorEnum.BLACK);
        int valueFourthTest = cardEffect.reductionOnDice(DiceAndFamilyMemberColorEnum.ORANGE);

        assertEquals(1, valueTest);
        assertEquals(0, valueSecondTest);
        assertEquals(1, valueThirdTest);
        assertEquals(0, valueFourthTest);

    }

}