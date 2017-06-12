package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Testing skipRound excommunication
 */
public class SkipRoundEffectTest {

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
    public void skipRound() throws Exception {
        boolean valueTest = excommunicationTiles.get(14).effect.skipRound();
        assertTrue(valueTest);

    }

}