package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import javax.smartcardio.Card;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests MalusDiceOnTowerColor
 */
public class MalusDiceOnTowerColorEffectTest {
    ArrayList<ExcommunicationTile> excommunicationTiles;
    AbstractExcommunicationTileEffect cardEffect;
    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception in case JSON doesn't load properly
     */
    @Before
    public void setUp() throws Exception {
        //setting up test for our specific card
        JSONLoader.instance();
        excommunicationTiles = JSONLoader.loadExcommunicationTiles();
        //setting up test for the specific method
        cardEffect = new MalusDiceOnTowerColorEffect(1, CardColorEnum.GREEN);
    }
    @Test
    public void malusDiceOnTowerColor() throws Exception {
        //those 2 lines tests the card loaded
        int valueTestNull = excommunicationTiles.get(7).effect.malusDiceOnTowerColor(CardColorEnum.YELLOW);
        int valueSecondTestNull = excommunicationTiles.get(7).effect.reductionOnDice(DiceAndFamilyMemberColorEnum.NEUTRAL);
        int valueTest = excommunicationTiles.get(7).effect.malusDiceOnTowerColor(CardColorEnum.GREEN);
        int valueSecondTest = excommunicationTiles.get(7).effect.malusDiceOnTowerColor(CardColorEnum.PURPLE);
        //those 2 tests the effect itself
        int valueThirdTest = cardEffect.malusDiceOnTowerColor(CardColorEnum.GREEN);
        int valueFourthTest = cardEffect.malusDiceOnTowerColor(CardColorEnum.PURPLE);

        assertEquals(0, valueTestNull);
        assertEquals(0, valueSecondTestNull);
        assertEquals(4, valueTest);
        assertEquals(0, valueSecondTest);
        assertEquals(1, valueThirdTest);
        assertEquals(0, valueFourthTest);

    }

}