package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 26/05/2017.
 */
public class BonusOnTowerEffectChoice extends BonusOnTowerEffect {
    private Resource discountOnSecondResource;
    public BonusOnTowerEffectChoice(CardColorEnum towerColor, Resource resourceDiscount, Resource secondDiscount, int diceDiscount)
    {
        super(towerColor, resourceDiscount, diceDiscount);
        this.discountOnSecondResource = secondDiscount;
    }

}
