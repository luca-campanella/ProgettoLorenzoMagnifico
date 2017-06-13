package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * testing haverst malus
 */
public class HarvestMalusEffectTest {
    ArrayList<ExcommunicationTile> excommunicationTiles;

    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception in case JSON doesn't load cards properly
     */
    @Before
    public void setUp() throws Exception {
        JSONLoader.instance();
        excommunicationTiles = JSONLoader.loadExcommunicationTiles();

    }
    @Test
    public void harvestMalusEffect() throws Exception {
        int valueTest;
        valueTest = excommunicationTiles.get(6).effect.harvestMalusEffect();
        assertEquals(3, valueTest);

    }

}