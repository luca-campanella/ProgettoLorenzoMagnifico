package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

import javax.smartcardio.Card;

/**
 * Created by higla on 23/05/2017.
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



}
