package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.effects.TakeOrPaySomethingEffect;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * the personal board of a single player
 */
public class PersonalBoard {

    private HashMap<CardColorEnum, ArrayList<AbstractCard>> ownedCards;

    TakeOrPaySomethingEffect bonusTileHarvestEffect;
    TakeOrPaySomethingEffect bonusTileBuildEffect;

    public PersonalBoard(TakeOrPaySomethingEffect bonusTileHarvestEffect, TakeOrPaySomethingEffect bonusTileBuildEffect){

        this.bonusTileHarvestEffect = bonusTileHarvestEffect;
        this.bonusTileBuildEffect = bonusTileBuildEffect;

    }

    public void addCard(AbstractCard card, CardColorEnum color){

        ownedCards.get(color).add(card);

    }
}
