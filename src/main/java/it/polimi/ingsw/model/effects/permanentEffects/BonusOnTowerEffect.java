package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class BonusOnTowerEffect extends AbstractPermanentEffect{
    private CardColorEnum color;
    private Resource discount;

    /**
     * this method should returns a player the right discount.
     * It doesn't, it should be rethought. For example -2 on diceGreen how do you handle?
     * @param color
     * @return
     */
    public Resource getBonusOnTower(CardColorEnum color)
    {
        return discount;
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
}
