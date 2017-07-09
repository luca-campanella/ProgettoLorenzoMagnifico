package it.polimi.ingsw.model.player;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

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
     * This array is used to check if the player has enough military points
     * to place the territory card on his personal board
     */
    private int militaryPointsTerritoryRequirements[] = {0, 0, 3, 7, 12, 18};

    /**
     * This array is used to calculate the corresponding victory points on territory cards at the end of the game
     */
    private int victoryPointsTerritory[] = {0,0,1,4,10,20};

    /**
     * This array is used to calculate the corresponding victory points on character cards at the end of the game
     */
    private int victoryPointsCharacter[] = {1,3,6,10,15,21};
    /**
     * It represent the bonuses of the tiles near the personal board
     */
    private PersonalTile personalTile;


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
        Debug.printVerbose("personalBoard.build");
        //we check if there is some blue card that has a bonus on build, in this case we should modify the value of the dice
        final int realDiceValueBlue = realDiceValueNoBlueBonus + characterCardsCollector.getBonusOnBuild();
        buildingCards.forEach(card -> card.applyEffectsFromBuildToPlayer(player, realDiceValueBlue, choicesController));
        Debug.printVerbose("after foreach on build");

        //We add bonus tiles afterwards because the resources got from the bonus tiles should not count on the checks for the yellow cards
        personalTile.activateEffectsOnBuild(player, choicesController);
    }

    /**
     * this method is called to add all the purple points to the player
     * @param player the player you had to add the purple point
     */
    public void purplePoints(Player player) {

        for (VentureCard purpleCard : ventureCards) {
            player.addResource(purpleCard.getVictoryEndPoints());
        }
    }

    public int getNumberOfColoredCard(CardColorEnum color) {

        return getCardListByColor(color).size();
    }

    public LinkedList<? extends AbstractCard> getCardListByColor(CardColorEnum color) {
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

    public void setPersonalTile(PersonalTile personalTile){
        this.personalTile = personalTile;
        Debug.printVerbose("setted personal tile");
    }

    public PersonalTile getPersonalTile() {
        return personalTile;
    }


    public CharacterCardCollector getCharacterCardsCollector() {
        return characterCardsCollector;
    }

    /**
     * This method checks that there is enough space on the personal board to take a territory card and plus
     * checks if the requirement on military points is met
     * @param currentMilitaryPoints the number of actual military points the player has right now
     * @param noMilitaryPointsNeededForTerritoryCardsLeaderAbility should be true if the player has a leader with this ability
     * @return true if he can take the card
     */
    public boolean canAddTerritoryCard(int currentMilitaryPoints, boolean noMilitaryPointsNeededForTerritoryCardsLeaderAbility) {

        if(territoryCards.size() < 6 &&
                ((militaryPointsTerritoryRequirements[territoryCards.size()] <= currentMilitaryPoints) ||
                        (noMilitaryPointsNeededForTerritoryCardsLeaderAbility)))
            return true;
        return false;
    }

    public LinkedList<TerritoryCard> getTerritoryCards() {
        return territoryCards;
    }

    /**
     * this method is called by the player of the personal board to add the victory points based on the number of green cards to a player
     * @param player the player to add the victory points, in this method the player is the owner of the personal board
     */
    public void greenPoints(Player player) {

        //if the player doesn't have territory cards the method return
        if(territoryCards.size() == 0)
            return;
        player.addResource(new Resource(ResourceTypeEnum.VICTORY_POINT, victoryPointsTerritory[territoryCards.size()-1]));

    }

    /**
     * this method is called by the player of the personal board to add the victory points based on the number of blue cards to a player
     * @param player the player to add the victory points, in this method the player is the owner of the personal board
     */
    public void bluePoints(Player player) {

        //if the player doesn't have character cards the method return
        if(characterCardsCollector.getCharacterCards().size() == 0)
            return;
        player.addResource(new Resource(ResourceTypeEnum.VICTORY_POINT,
                victoryPointsCharacter[characterCardsCollector.getCharacterCards().size()]));

    }
}
