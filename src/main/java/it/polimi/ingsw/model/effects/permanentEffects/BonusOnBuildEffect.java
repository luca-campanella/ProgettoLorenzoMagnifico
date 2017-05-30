package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This class handles all the effects when you build. In particular.
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
    public String getShortDescription(){
        return "+"+bonus+" OnB";
    }
    public String getDescription(){
        return "gives a player " + bonus + " on the dice, when he builds";
    }
}
