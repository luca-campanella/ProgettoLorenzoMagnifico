package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests noVPonColoredCard excommunication
 */
public class NoVPOnColoredCardTest {

    ArrayList<ExcommunicationTile> excommunicationTiles;
    AbstractExcommunicationTileEffect cardEffect;
    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception in case json doesn't load properly
     */
    @Before
    public void setUp() throws Exception {
        //setting up test for our specific card
        JSONLoader.instance();
        excommunicationTiles = JSONLoader.loadExcommunicationTiles();
    }
    @Test
    public void noVPColoredCard() throws Exception {
        boolean valueTest = excommunicationTiles.get(15).effect.noVPColoredCard(CardColorEnum.GREEN);
        boolean valueSecondTest = excommunicationTiles.get(16).effect.noVPColoredCard(CardColorEnum.BLUE);
        boolean valueThirdTest = excommunicationTiles.get(17).effect.noVPColoredCard(CardColorEnum.PURPLE);
        boolean valueFourthTest = excommunicationTiles.get(17).effect.noVPColoredCard(CardColorEnum.GREEN);

        assertTrue(valueTest);
        assertTrue(valueSecondTest);
        assertTrue(valueThirdTest);
        assertFalse(valueFourthTest);

    }

}