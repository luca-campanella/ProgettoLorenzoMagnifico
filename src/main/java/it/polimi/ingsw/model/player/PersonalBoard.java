package it.polimi.ingsw.model.player;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.AbstractActionSpace;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.*;

import javax.smartcardio.Card;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * the personal board of a single player
 */
public class PersonalBoard implements Serializable{

    private LinkedList<TerritoryCard> territoryCards;
    private LinkedList<BuildingCard> buildingCards;
    private CharacterCardCollector characterCardsCollector;
    private LinkedList<VentureCard> ventureCards;

    /**
     * It represent the bonuses of the tiles near the personal board
     */
    private PersonalTile personalTile = null;


    public PersonalBoard() {
        territoryCards = new LinkedList<TerritoryCard>();
        buildingCards = new LinkedList<BuildingCard>();
        characterCardsCollector = new CharacterCardCollector();
        ventureCards = new LinkedList<VentureCard>();
    }

    public void addCard(AbstractCard card)
    {
        switch(card.getColor()) {
            case GREEN: addGreenTerritoryCard((TerritoryCard)card);
            break;
            case BLUE: addBlueCharacterCard((CharacterCard) card);
            break;
            case YELLOW: addYellowBuildingCard((BuildingCard)card);
            break;
            case PURPLE: addPurpleVentureCard((VentureCard)card);
            break;
        }
    }
    public void addGreenTerritoryCard(TerritoryCard card) {
        territoryCards.add(card);
    }

    public void addYellowBuildingCard(BuildingCard card) {
        buildingCards.add(card);
    }

    public void addBlueCharacterCard(CharacterCard card) {
        characterCardsCollector.add(card);
    }

    public void addPurpleVentureCard(VentureCard card) {
        ventureCards.add(card);
    }

    public LinkedList<BuildingCard> getYellowBuildingCards() {
        return buildingCards;
    }

    public void harvest(int realDiceValueNoBlueBonus, Player player, ChoicesHandlerInterface choicesController) {

        //we check if there is some blue card that has a bonus on harvest, in this case we should modify the value of the dice
        final int realDiceValueBlue = realDiceValueNoBlueBonus + characterCardsCollector.getBonusOnHarvest();

        territoryCards.forEach(card -> card.applyEffectsFromHarvestToPlayer(player, realDiceValueBlue, choicesController));

        personalTile.activateEffectsOnHarvest(player, choicesController);
    }

    public void build(int realDiceValueNoBlueBonus, Player player, ChoicesHandlerInterface choicesController) {

        //we check if there is some blue card that has a bonus on build, in this case we should modify the value of the dice
        final int realDiceValueBlue = realDiceValueNoBlueBonus + characterCardsCollector.getBonusOnBuild();

        buildingCards.forEach(card -> card.applyEffectsFromBuildToPlayer(player, realDiceValueBlue, choicesController));

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
                return characterCardsCollector.getCharacterCards();
            case PURPLE:
                return ventureCards;
        }

        return null;
    }

    public PersonalTile getPersonalTile() {
        return personalTile;
    }


    public CharacterCardCollector getCharacterCardsCollector() {
        return characterCardsCollector;
    }

    @Deprecated
    public LinkedList<CharacterCard> getCharacterCards() {
        return characterCardsCollector.getCharacterCards();
    }
}
