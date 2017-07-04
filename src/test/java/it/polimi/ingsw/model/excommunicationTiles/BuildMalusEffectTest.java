package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Testing buildDiceMalusEffect
 */
public class BuildMalusEffectTest {

    ArrayList<ExcommunicationTile> excommunicationTiles;

    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception in case file doesn't load properly
     */
    @Before
    public void setUp() throws Exception {
        JSONLoader.instance();
        excommunicationTiles = JSONLoader.loadExcommunicationTiles();

    }
    @Test
    public void buildMalusEffect() throws Exception {
        int valueTest;
        valueTest = excommunicationTiles.get(4).effect.buildDiceMalusEffect();
        assertEquals(3, valueTest);

    }

}