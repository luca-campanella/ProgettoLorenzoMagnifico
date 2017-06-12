package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This tests if market is available
 */
public class MalusOnMarketEffectTest {

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
    }
    @Test
    public void marketNotAvailable() throws Exception {
       boolean valueTest = excommunicationTiles.get(12).effect.marketNotAvailable();
        assertTrue(valueTest);
    }

}