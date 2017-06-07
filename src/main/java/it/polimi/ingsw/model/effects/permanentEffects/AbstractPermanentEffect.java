package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

import java.io.Serializable;

/**
 * This is the abstract class that handles all abstract effects.
 * Controllers will call this class in order to check if a card applies a particular effect to a particular action
 */
public abstract class AbstractPermanentEffect implements Serializable{

        /**
         * This method is used to get the discount when placing a family member on a certain tower
         * this method is overriden by {@link BonusOnTowerEffect} to return the correct discount
         * @param color the color of the tower / card
         * @return the resource discounted
         */
        public Resource getDiscountOnTower(CardColorEnum color)
        {
                return null;
        }
        /**
         * This method is used to get the bonus on the dice when placing a family member on a certain tower
         * this method is overriden by {@link BonusOnTowerEffect} to return the correct bonus
         * @param color the color of the tower / card
         * @return the bonus on the dice of the family member placed on that tower
         */
        public int getBonusOnDice(CardColorEnum color) {
                return 0;
        }

        /**
         * This method returns the bonus on the dice when the player performs a harvest action
         * This method is overriden by {@link BonusOnHarvestEffect} to return the right value
         * @return 0
         */
        public int getBonusOnHarvest()
        {
                return 0;
        }

        /**
         * This method returns the bonus on the dice when the player performs a build action
         * This method is overriden by {@link BonusOnBuildEffect} to return the right value
         * @return 0
         */
        public int getBonusOnBuild()
        {
                return 0;
        }

        /**
         * This method returns if the player has immediate effects disabled on a certain level of towers, that has a certain dice requirement
         * This method will be overridden by {@link MalusDisabledImmediateEffectsEffect} to return the correct value
         * @param requiredDiceValue the required dice value check if at that level the effects are disabled
         * @return false, by default
         */
        public boolean isImmediateEffectDisabled(int requiredDiceValue)
        {
                return false;
        }

        public abstract String getShortDescription();
        public abstract String getDescription();
}
