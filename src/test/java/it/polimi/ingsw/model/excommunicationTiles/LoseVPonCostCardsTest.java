package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.effects.immediateEffects.PayResourceEffect;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * this class tests LoseVPonCostCards
 */
public class LoseVPonCostCardsTest {

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


    /**
     * This method tests if the effect returns the correct value
     * @throws Exception
     */
    @Test
    public void loseVPonCosts() throws Exception {
        int kalkulatedByFunctionTesting = 0;
        kalkulatedByFunctionTesting = excommunicationTiles.get(20).effect.loseVPonCosts(deck.getBuildingCards());
        int calculatedHandly = 0;
        for(int i = 0; i<deck.getBuildingCards().size();i++)
            for(PayResourceEffect iterator : deck.getBuildingCards().get(i).getCost())
                calculatedHandly += iterator.getCost().getValue();
        assertEquals(calculatedHandly, kalkulatedByFunctionTesting);
        assertEquals(109, kalkulatedByFunctionTesting);
    }
    //it works
}


