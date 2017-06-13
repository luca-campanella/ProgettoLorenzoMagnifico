package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * creating test on excommunication cards
 */
public class GainFewerResourceEffectTest {
    JSONLoader jsonLoader;
    ArrayList<ExcommunicationTile> excommunicationTiles;
    GainFewerResourceEffect cardEffect;
    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        JSONLoader.instance();
        excommunicationTiles = JSONLoader.loadExcommunicationTiles();
        //creating a resource test
        Resource resource = new Resource(ResourceTypeEnum.COIN, 1);
        cardEffect = new GainFewerResourceEffect(resource);
    }
    @Test
    public void gainFewerResourceEffect() throws Exception {

        int valueTestOnBuild = -1;

        Resource resourceTestOne = new Resource(ResourceTypeEnum.COIN, 5);
        Resource resourceTestZero = new Resource(ResourceTypeEnum.WOOD, 5);

        valueTestOnBuild = excommunicationTiles.get(1).effect.buildMalusEffect();

        int valueTestOnTileZero = excommunicationTiles.get(1).effect.gainFewResource(resourceTestZero);
        int valueTestOnTiletOne = excommunicationTiles.get(1).effect.gainFewResource(resourceTestOne);

        int valueTestOnEffectZero = cardEffect.gainFewResource(resourceTestZero);
        int valueTestOnEffectOne = cardEffect.gainFewResource(resourceTestOne);


        assertEquals(0, valueTestOnBuild);
        assertEquals(0, valueTestOnTileZero);
        assertEquals(1, valueTestOnTiletOne);
        assertEquals(0, valueTestOnEffectZero);
        assertEquals(1, valueTestOnEffectOne);

    }

}