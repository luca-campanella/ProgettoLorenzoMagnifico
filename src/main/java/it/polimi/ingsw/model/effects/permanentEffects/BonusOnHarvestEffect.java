package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class BonusOnHarvestEffect extends AbstractPermanentEffect {
    private int bonus;

    public BonusOnHarvestEffect(int bonus) {
        this.bonus = bonus;
    }

    public int getBonusOnHarvest() {
        return bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getShortDescription() {
        return "+" + bonus + " OnH";
    }

    public String getDescription() {
        return "gives a player " + bonus + " on the dice, when he harvests";
    }
}
