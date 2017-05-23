package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public abstract class AbstractPermanentEffect {
        abstract Resource getBonusOnTower(CardColorEnum color);
        abstract public int getBonusOnHarvest();
        abstract public int getBonusOnBuild();
        abstract public boolean isImmediateEffectDisabled();

}
