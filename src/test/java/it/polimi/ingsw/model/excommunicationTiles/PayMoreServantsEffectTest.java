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

    JSONLoader jsonLoader;
    Deck deck;
    ArrayList<ExcommunicationTile> excommunicationTiles;

    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        jsonLoader = new JSONLoader();
        deck = jsonLoader.createNewDeck();
        excommunicationTiles = jsonLoader.loadExcommunicationTiles();

    }
    @Test
    public void payMoreServant() throws Exception {
        int valueTest = excommunicationTiles.get(13).effect.payMoreServant();
        assertEquals(1, valueTest);
    }

}