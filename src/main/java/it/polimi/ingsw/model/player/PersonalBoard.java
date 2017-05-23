package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.AbstractActionSpace;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * the personal board of a single player
 */
public class PersonalBoard {

    /**
     * the list of the cards owned
     * CardColorEnum is the color of the card
     * ArrayList is the list of cards of that color
     */
    private HashMap<CardColorEnum, ArrayList<AbstractCard>> ownedCards;

    /**
     * the two following attributes represent the bouses of the tiles near the personal board
     */
    TakeOrPaySomethingEffect bonusTileHarvestEffect;
    TakeOrPaySomethingEffect bonusTileBuildEffect;

    public PersonalBoard(){

        ownedCards = new HashMap<>();


    }

    public void setTiles(TakeOrPaySomethingEffect bonusTileHarvestEffect, TakeOrPaySomethingEffect bonusTileBuildEffect){

        this.bonusTileHarvestEffect = bonusTileHarvestEffect;
        this.bonusTileBuildEffect = bonusTileBuildEffect;

    }

    public void addCard(AbstractCard card, CardColorEnum color){

        ownedCards.get(color).add(card);

    }

    public void harvest(int familyMemberValue, Player player){

        //TODO bonuses of the blue card
        bonusTileHarvestEffect.applyToPlayer(player);
        ArrayList<AbstractCard> greenCard = ownedCards.get(CardColorEnum.GREEN);
        for(AbstractCard i : greenCard){
    //        i.harvest(familyMemberValue, player);

        }
    }

    public void building(int familyMemberValue, Player player){

        //TODO bonuses of the blue card
        bonusTileBuildEffect.applyToPlayer(player);
        ArrayList<AbstractCard> yellowCard = ownedCards.get(CardColorEnum.YELLOW);
        for(AbstractCard i : yellowCard){
    //        i.building(familyMemberValue, player);

        }
    }

    public void blueBonus(AbstractActionSpace space){

        //TODO bonus
    }

    public void purplePoints(Player player){

        ArrayList<AbstractCard> purpleCard = ownedCards.get(CardColorEnum.PURPLE);
        for(AbstractCard i : purpleCard) {
            //i.purplePoints(player);
        }
    }

    public int getNumberOfColoredCard(CardColorEnum color){

        return ownedCards.get(color).size();
    }
}
