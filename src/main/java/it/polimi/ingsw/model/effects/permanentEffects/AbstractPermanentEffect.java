package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This is the abstract class that handles all abstract effects.
 * Controllers will call this class in order to check if a card applies a particular effect to a particular action
 */
public abstract class AbstractPermanentEffect {

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
         * This method is used to get the discount when placing a family member on a certain tower
         * this method is overriden by {@link BonusOnTowerEffect} to return the correct bonus
         * @param color the color of the tower / card
         * @return the bonus on the dice of the family member placed on that tower
         */
        public int getBonusOnDice(CardColorEnum color) {
                return 0;
        }
        public int getBonusOnHarvest()
        {
                return 0;
        }
        public int getBonusOnBuild()
        {
                return 0;
        }

        public boolean isImmediateEffectDisabled()
        {
                return false;
        }
        public abstract String getShortDescription();
        public abstract String getDescription();
}
