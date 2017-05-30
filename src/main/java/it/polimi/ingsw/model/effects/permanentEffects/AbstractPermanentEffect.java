package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

import javax.smartcardio.Card;

/**
 * This is the abstract class that handles all abstract effects.
 * Controllers will call this class in order to check if a card applies a particular effect to a particular action
 */
public abstract class AbstractPermanentEffect {
        public Resource getBonusOnTower(CardColorEnum color)
        {
                return null;
        }
        public int getBonusOnHarvest()
        {
                return 0;
        }
        public int getBonusOnBuild()
        {
                return 0;
        }
        public int getDiscountOnDice(CardColorEnum color) {
                return 0;
        }
        public boolean isImmediateEffectDisabled()
        {
                return false;
        }
        public abstract String getShortDescription();
        public abstract String getDescription();
}
