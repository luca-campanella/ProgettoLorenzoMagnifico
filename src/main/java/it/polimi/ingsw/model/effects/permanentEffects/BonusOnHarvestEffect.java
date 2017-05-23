package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;

/**
 * Created by higla on 23/05/2017.
 */
public class BonusOnHarvestEffect {
    private int bonus;
    public int getBonusOnTower(CardColorEnum color)
    {
        return 0;
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
