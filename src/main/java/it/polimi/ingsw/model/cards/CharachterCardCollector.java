package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;
import it.polimi.ingsw.model.resource.Resource;

import java.util.LinkedList;

/**
 * Created by campus on 06/06/2017.
 */
public class CharachterCardCollector {

    private LinkedList<CharacterCard> characterCards;

    /**
     * This method is used to get the discount when placing a family member on a certain tower
     * It passes all the cards and returns the corresponding LinkedList of resources
     * At the effect level this method is overriden by {@link it.polimi.ingsw.model.effects.permanentEffects.BonusOnTowerEffect} to return the correct discount
     * @param color the color of the tower / card
     * @return the resource discounted
     */
    public LinkedList<Resource> getDiscountOnTower(CardColorEnum color)
    {
        LinkedList<Resource> discount = new LinkedList<>();
        for(CharacterCard cardIter : characterCards) {
            for(AbstractPermanentEffect effectIter : cardIter.getPermanentEffects()) {
                discount.add(effectIter.getDiscountOnTower(color));
            }
        }
        return discount;
    }

    /**
     * This method is used to get the discount when placing a family member on a certain tower
     * this method is overriden by {@link it.polimi.ingsw.model.effects.permanentEffects.BonusOnTowerEffect} to return the correct bonus
     * @param color the color of the tower / card
     * @return the bonus on the dice of the family member placed on that tower
     */
    public int getBonusOnDice(CardColorEnum color) {
        int diceValue = 0;
        for(CharacterCard cardIter : characterCards) {
            for(AbstractPermanentEffect effectIter : cardIter.getPermanentEffects()) {
                diceValue += effectIter.getBonusOnDice(color);
            }
        }

        return diceValue;
    }

    /**
     * This method returns the bonus on the dice when the player performs a harvest action
     * This method is overriden by {@link it.polimi.ingsw.model.effects.permanentEffects.BonusOnHarvestEffect} to return the right value
     * @return 0
     */
    public int getBonusOnHarvest()
    {

        int diceValue = 0;
        for(CharacterCard cardIter : characterCards) {
            for(AbstractPermanentEffect effectIter : cardIter.getPermanentEffects()) {
                diceValue += effectIter.getBonusOnHarvest();
            }
        }

        return diceValue;
    }

    /**
     * This method returns the bonus on the dice when the player performs a build action
     * This method is overriden by {@link it.polimi.ingsw.model.effects.permanentEffects.BonusOnBuildEffect} to return the right value
     * @return 0
     */
    public int getBonusOnBuild()
    {
        int diceValue = 0;
        for(CharacterCard cardIter : characterCards) {
            for(AbstractPermanentEffect effectIter : cardIter.getPermanentEffects()) {
                diceValue += effectIter.getBonusOnBuild();
            }
        }

        return diceValue;
    }

    /**
     * This method returns if the player has immediate effects disabled on a certain level of towers
     * This method will be overridden by {@link it.polimi.ingsw.model.effects.permanentEffects.MalusDisabledImmediateEffectsEffect} to return the correct value
     * @param towerLevel the level of the tower to check if at that level the effects are disabled
     * @return false
     */
    public boolean isImmediateEffectDisabled(int towerLevel)
    {
        return false;
    }

}
