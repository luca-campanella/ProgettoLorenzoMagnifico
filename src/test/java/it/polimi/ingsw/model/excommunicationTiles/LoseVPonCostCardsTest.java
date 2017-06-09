package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.effects.immediateEffects.PayResourceEffect;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * this class tests LoseVPonCostCards
 */
public class LoseVPonCostCardsTest {

    //testing the method with all building cards.
    @Test
    public void loseVPonCosts() throws Exception {
        int kalkulatedByFunctionTesting = 0;
        JSONLoader jsonLoader = new JSONLoader();
        Deck deck = jsonLoader.createNewDeck();
        ArrayList<ExcommunicationTile> excommunicationTiles = jsonLoader.loadExcommunicationTiles();
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


