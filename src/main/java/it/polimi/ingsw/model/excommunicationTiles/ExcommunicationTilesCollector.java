package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;

/**
 * This class is created as a collector of tiles inside the player
 * In this way it is sufficient to call methods on this class to get the mali,
 * instead of looping on all tiles the user has
 */
public class ExcommunicationTilesCollector {

    ArrayList<ExcommunicationTile> tiles;

    //The following list is a list all of the first period malus effects

    public ExcommunicationTilesCollector(ArrayList<ExcommunicationTile> tiles) {
        this();
        this.tiles = tiles;
    }

    public ExcommunicationTilesCollector() {
        tiles = new ArrayList<ExcommunicationTile>(3);
    }

    /**
     * This method returns the malus on the resource of that type
     * For example
     * <i>Each time you receive coins (from action spaces or from your Cards), you receive 1 fewer coin. (If you
     * have more Cards that give you coins, consider each Card a single source, so you receive -1 coin for each card.)</i>
     *
     * @param resourceType the resource to check on
     * @return the resource malus, positive, should be subtracted
     */
    public int gainFewResource(ResourceTypeEnum resourceType){
        int malus = 0;
        for(ExcommunicationTile tileIter : tiles)
            malus += tileIter.getEffect().gainFewResource(resourceType);
        return malus;
    }

    /**
     * whenever you harvest, you harvest with a malus on the dice.
     * @return the malusValue
     */
    public int harvestDiceMalusEffect()
    {
        int malus = 0;
        for(ExcommunicationTile tileIter : tiles)
            malus += tileIter.getEffect().harvestDiceMalusEffect();
        return malus;
    }

    /**
     * whenever you build, you build with a malus on the dice.
     * @return the malusValue
     */
    public int buildDiceMalusEffect()
    {
        int malus = 0;
        for(ExcommunicationTile tileIter : tiles)
            malus += tileIter.getEffect().buildDiceMalusEffect();
        return malus;
    }

    /**
     * whenever are using a certain family member its value is reduced by the value returned
     * For example:
     * <i>All your colored Family Members receive a -1 reduction of their value each time you place them. (For example,
     * if you roll a 5 on the black die, your Family Member with the black die symbol has a value of 4.)
     * You may still spend servants to increase their value and you must apply your Card’s effects.</i>
     * @param familyMemberColor you dice has a malus if the family member color is affected
     * @return the value of the malus
     */
    public int reductionOnDice(DiceAndFamilyMemberColorEnum familyMemberColor)
    {
        int malus = 0;
        for(ExcommunicationTile tileIter : tiles)
            malus += tileIter.getEffect().reductionOnDice(familyMemberColor);
        return malus;
    }

    //The following list is a list all of the second period malus effects

    /**
     * this method returns the malusValue of the dice on a certain tower / card color
     * For example:
     * <i>Each time you take a Territory Card (through the action space or as a Card effect), your action receives
     * a -4 reduction of its value. You may still spend servants to increase the action value and you must apply
     * your Card’s effects.</i>
     * @param colorOfTower the color of the tower to check on
     * @return the malus
     */
    public int malusDiceOnTowerColor(CardColorEnum colorOfTower)
    {
        int malus = 0;
        for(ExcommunicationTile tileIter : tiles)
            malus += tileIter.getEffect().malusDiceOnTowerColor(colorOfTower);
        return malus;
    }

    /**
     * this method doesn't allow a player to place an action player on market
     * @return true if you can't place it
     */
    public boolean marketNotAvailable()
    {
        for(ExcommunicationTile tileIter : tiles)
            if(tileIter.getEffect().marketNotAvailable())
                return true;
        return false;
    }

    /**
     * this method indicates how many more servants a player has to pay to have +1 on action value
     * @return the exchange rate >> return value : 1 family member
     */
    public int payMoreServant()
    {
        int malus = 1;
        int tmp;
        for(ExcommunicationTile tileIter : tiles) {
            tmp = tileIter.getEffect().payMoreServant();
            if(tmp > 1)
                malus += tmp;
        }
        return malus;
    }

    /**
     * this method signals that player has to skip the round.
     * <i>Each round, you skip your  rst turn. (When you have to place your first Family Member, you have to pass.) You start taking actions from the second turn (in the appropriate turn order.) When all players have taken all their turns, you may still place your last Family Member.</i>
     * @return true if he has to skip
     */
    //todo inject in model
    public boolean skipFirstTurn(){
        for(ExcommunicationTile tileIter : tiles)
            if(tileIter.getEffect().skipFirstTurn())
                return true;
        return false;
    }

    //3rd period excommunication

    /**
     * this method returns true if you don't score any point for a certain
     * @param color ed card
     * @return s true if you don't get VP. False if you get VP
     */
    //todo insert in ending phase
    public boolean noVPColoredCard(CardColorEnum color)
    {
        return false;
    }

    /**
     * this method returns a value different from 0 when a player has an excommunication Tile that let lose
     * victory points to the player.
     * @param resource are the resource of the player
     * @return the number of victory points lost
     */
    //todo insert in ending phase
    public int noVPonResource(ArrayList<Resource> resource)
    {
        return 0;
    }

    /**
     * this method let player lose VP for each resource costed resource on yellow cards
     * @param cards is the list of yellow cards that a player owns
     * @return
     */
    //todo insert in ending phase
    public int loseVPonCosts(ArrayList<BuildingCard> cards){ return 0;}

    public void addExcommunicationTile(ExcommunicationTile tile) {
        tiles.add(tile);
    }
}
