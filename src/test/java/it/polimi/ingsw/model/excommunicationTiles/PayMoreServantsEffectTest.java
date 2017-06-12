package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests PayMoreServants Effect.
 */
public class PayMoreServantsEffectTest {

    Deck deck;
    ArrayList<ExcommunicationTile> excommunicationTiles;

    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception in case json can't access the file
     */
    @Before
    public void setUp() throws Exception {
        JSONLoader.instance();
        deck = JSONLoader.createNewDeck();
        excommunicationTiles = JSONLoader.loadExcommunicationTiles();

    }
    @Test
    public void payMoreServant() throws Exception {
        int valueTest = excommunicationTiles.get(13).effect.payMoreServant();
        assertEquals(1, valueTest);
    }

}