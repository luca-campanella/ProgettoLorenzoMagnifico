package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This effect disables all immediate actions space effect
 */
public class MalusDisabledImmediateEffectsEffect extends  AbstractPermanentEffect{
    //private int[] towerLevelsDisabled;

    public boolean isImmediateEffectDisabled()
    {
        return true;
    }
}
