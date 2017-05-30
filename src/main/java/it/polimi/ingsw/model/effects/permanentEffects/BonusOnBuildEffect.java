package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class BonusOnBuildEffect extends AbstractPermanentEffect{
    private int bonus;
    public BonusOnBuildEffect(int bonus){
        this.bonus = bonus;
    }
    public int getBonusOnBuild()
    {
        return bonus;
    }
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
