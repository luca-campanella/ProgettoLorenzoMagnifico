package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Testing buildMalusEffect
 */
public class BuildMalusEffectTest {
    JSONLoader jsonLoader;
    ArrayList<ExcommunicationTile> excommunicationTiles;

    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        jsonLoader = new JSONLoader();
        excommunicationTiles = jsonLoader.loadExcommunicationTiles();

    }
    @Test
    public void buildMalusEffect() throws Exception {
        int valueTest;
        valueTest = excommunicationTiles.get(4).effect.buildMalusEffect();
        assertEquals(3, valueTest);

    }

}