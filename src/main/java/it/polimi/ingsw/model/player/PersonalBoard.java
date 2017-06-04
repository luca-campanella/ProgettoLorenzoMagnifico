package it.polimi.ingsw.model.player;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.AbstractActionSpace;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;

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
     * It represent the bonuses of the tiles near the personal board
     */
    private PersonalTile personalTile = null;


    public PersonalBoard() {
        territoryCards = new LinkedList<TerritoryCard>();
        buildingCards = new LinkedList<BuildingCard>();
        characterCards = new LinkedList<CharacterCard>();
        ventureCards = new LinkedList<VentureCard>();
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

    public void harvest(int realDiceValueNoBlueBonus, Player player, ChoicesHandlerInterface choicesController) {

        //TODO bonuses of the blue card
        //bonusTileHarvestEffect.applyToPlayer(player);
        /*LinkedList<AbstractCard> greenCard = ownedCards.get(CardColorEnum.GREEN);
        for (AbstractCard i : greenCard) {
            //        i.harvest(familyMemberValue, player);

        }*/
    }

    public void build(int realDiceValueNoBlueBonus, Player player, ChoicesHandlerInterface choicesController) {

        //we check if there is some blue card that has a bonus on build, in this case we should modify the value of the dice
        int realDiceValueBlue = realDiceValueNoBlueBonus;

        for(CharacterCard cardIter : characterCards) {
            for(AbstractPermanentEffect effectIter : cardIter.getPermanentEffects()) {
                realDiceValueBlue += effectIter.getBonusOnBuild();
            }
        }

        final int realDiceValueBlueFinal = realDiceValueBlue; //to pass to the lambda expr
        buildingCards.forEach(card -> card.applyEffectsToPlayer(player, realDiceValueBlueFinal, choicesController));

        //We add bonus tiles afterwards because the resources got from the bonus tiles should not count on the checks for the yellow cards
        personalTile.activateEffectsOnBuild(player, choicesController);
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

    public PersonalTile getPersonalTile() {
        return personalTile;
    }

    public LinkedList<CharacterCard> getCharacterCards() {
        return characterCards;
    }
}
