package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class BonusOnBuildEffect extends AbstractPermanentEffect{
    private int bonus;
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
        return bonus;
    }
    public boolean isImmediateEffectDisabled()
    {
        return false;
    }
}
