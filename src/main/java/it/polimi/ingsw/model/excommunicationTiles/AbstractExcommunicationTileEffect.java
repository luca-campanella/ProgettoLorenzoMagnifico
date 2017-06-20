package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class contains all the excommunication effects
 *
 */
public abstract class AbstractExcommunicationTileEffect implements Serializable {

    //The following list is a list all of the first period malus effects
    //it returns a POSITIVE resource. Then it will be subbed from Player

    /**
     * This method returns if the resource of thhis ttype has a malus
     * For example <i>Each time you receive coins (from action spaces or from your Cards), you receive 1 fewer coin. (If you have more Cards that give you coins, consider each Card a single source, so you receive -1 coin for each card.)</i>
     *
     * @param resourceType the resource to check on
     * @return the resource malus, positive, should be subtracted
     */
    public int gainFewResource(ResourceTypeEnum resourceType){
        return 0;
    }

    /**
     * whenever you harvest, you harvest with a malus on the dice.
     * @return the malusValue
     */
    public int harvestDiceMalusEffect()
    {
        return 0;
    }

    /**
     * whenever you build, you build with a malus on the dice.
     * @return the malusValue
     */
    public int buildDiceMalusEffect()
    {
        return 0;
    }

    /**
     * whenever you take a card using a certain family member
     * @param familyMemberColor you dice has a malus
     * @return the value of the malus
     */
    public int reductionOnDice(DiceAndFamilyMemberColorEnum familyMemberColor)
    {
        return 0;
    }


    //The following list is a list all of the second period malus effects

    /**
     * this method returns the malusValue of the dice on a certain
     * @param colorOfTower the color of the tower to check on
     * @return the malus
     */
    public int malusDiceOnTowerColor(CardColorEnum colorOfTower)
    {
        return 0;
    }

    /**
     * this method doesn't allow a player to place an action player on market
     * @return true if you can't place it
     */
    public boolean marketNotAvailable()
    {
        return false;
    }

    /**
     * this method indicates how many servants a player has to pay to have +1 on action value
     * @return the exchange rate, by defalt 1
     */
    public int payMoreServant()
    {
        return 1;
    }

    /**
     * this method signals that player has to skip the round.
     * <i>Each round, you skip your  rst turn. (When you have to place your first Family Member, you have to pass.) You start taking actions from the second turn (in the appropriate turn order.) When all players have taken all their turns, you may still place your last Family Member.</i>
     * @return
     */
    public boolean skipFirstTurn(){
        return false;
    }

    //3rd period excommunication

    /**
     * this method returns true if you don't score any point for a certain color of cards
     * @param color ed card
     * @return s true if you don't get VP. False if you get VP
     */
    public boolean noVPColoredCard(CardColorEnum color)
    {
        return false;
    }

    /**
     * this method returns a value different from 0 when a player has an excommunication Tile that let lose
     * victory points to the player.
     * <i>At the end of the game, you lose 1 Victory Point for every resource (wood, stone, coin, servant) in your supply on your Personal Board. (For example, if you end the game with 3 wood, 1 stone, 4 coins, and 2 servants, you lose 10 Victory Points.)</i>
     * @param resource are the resource of the player
     * @return the number of victory points lost
     */
    public int loseVPonResource(ArrayList<Resource> resource)
    {
        return 0;
    }

    /**
     * this method let player lose VP for each resource costed resource on yellow cards
     * <i>At the end of the game, you lose 1 Victory Point for every wood and stone on your Building Cards’ costs. (For example, if all your Building Cards cost 7 wood and 6 stone, you lose 13 Victory Points.)</i>
     * @param cards is the list of yellow cards that a player owns
     * @return
     */
    public int loseVPonCosts(ArrayList<BuildingCard> cards){ return 0;}




    public abstract String getShortEffectDescription();
}
