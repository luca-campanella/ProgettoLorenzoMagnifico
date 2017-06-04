package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.AbstractActionSpace;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;

import java.util.LinkedList;

/**
 * the personal board of a single player
 */
public class PersonalBoard {

    private LinkedList<TerritoryCard> territoryCards;
    private LinkedList<BuildingCard> buildingCards;
    private LinkedList<CharacterCard> characterCards;
    private LinkedList<VentureCard> ventureCards;

    /**
     * the two following attributes represent the bonuses of the tiles near the personal board
     */
    TakeOrPaySomethingEffect bonusTileHarvestEffect;
    TakeOrPaySomethingEffect bonusTileBuildEffect;

    public PersonalBoard() {
        territoryCards = new LinkedList<TerritoryCard>();
        buildingCards = new LinkedList<BuildingCard>();
        characterCards = new LinkedList<CharacterCard>();
        ventureCards = new LinkedList<VentureCard>();
    }

    public void setTiles(TakeOrPaySomethingEffect bonusTileHarvestEffect, TakeOrPaySomethingEffect bonusTileBuildEffect) {

        this.bonusTileHarvestEffect = bonusTileHarvestEffect;
        this.bonusTileBuildEffect = bonusTileBuildEffect;

    }

    public void addGreenTerritoryCard(TerritoryCard card) {
        territoryCards.add(card);
    }

    public void addYellowBuildingCard(BuildingCard card) {
        buildingCards.add(card);
    }

    public void addBlueCharacterCard(CharacterCard card) {
        characterCards.add(card);
    }

    public void addPurpleVentureCard(VentureCard card) {
        ventureCards.add(card);
    }

    public LinkedList<BuildingCard> getYellowBuildingCards() {
        return buildingCards;
    }

    public void harvest(int familyMemberValue, Player player) {

        //TODO bonuses of the blue card
        //bonusTileHarvestEffect.applyToPlayer(player);
        /*LinkedList<AbstractCard> greenCard = ownedCards.get(CardColorEnum.GREEN);
        for (AbstractCard i : greenCard) {
            //        i.harvest(familyMemberValue, player);

        }*/
    }

    public void building(int familyMemberValue, Player player) {

        //TODO bonuses of the blue card
        /*bonusTileBuildEffect.applyToPlayer(player);
        LinkedList<AbstractCard> yellowCard = ownedCards.get(CardColorEnum.YELLOW);
        for (AbstractCard i : yellowCard) {
            //        i.building(familyMemberValue, player);

        }*/
    }

    public void blueBonus(AbstractActionSpace space) {

        //TODO bonus
    }

    public void purplePoints(Player player) {

        /*LinkedList<AbstractCard> purpleCard = ownedCards.get(CardColorEnum.PURPLE);
        for (AbstractCard i : purpleCard) {
            //i.purplePoints(player);
        }*/
    }

    public int getNumberOfColoredCard(CardColorEnum color) {

        return getCardListByColor(color).size();
    }

    private LinkedList<? extends AbstractCard> getCardListByColor(CardColorEnum color) {
        switch(color) {
            case GREEN:
                return territoryCards;
            case YELLOW:
                return buildingCards;
            case BLUE:
                return characterCards;
            case PURPLE:
                return ventureCards;
        }

        return null;
    }
}
