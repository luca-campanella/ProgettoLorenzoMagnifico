package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class BonusOnTowerEffect extends AbstractPermanentEffect{
    private CardColorEnum towerColor;
    private Resource discountOnResource;
    private int discountOnDice;
    public BonusOnTowerEffect(CardColorEnum towerColor, Resource resourceDiscount, int diceDiscount)
    {
        this.towerColor = towerColor;
        this.discountOnResource = resourceDiscount;
        this.discountOnDice = diceDiscount;

    }

    public CardColorEnum getTowerColor() {
        return towerColor;
    }

    public void setTowerColor(CardColorEnum towerColor) {
        this.towerColor = towerColor;
    }

    public Resource getDiscountOnResource() {
        return discountOnResource;
    }

    public void setDiscountOnResource(Resource discountOnResource) {
        this.discountOnResource = discountOnResource;
    }

    public int getDiscountOnDice() {
        return discountOnDice;
    }

    public void setDiscountOnDice(int discountOnDice) {
        this.discountOnDice = discountOnDice;
    }

    /**
     * this method should returns a player the right discount.
     * It doesn't, it should be rethought. For example -2 on diceGreen how do you handle?
     * @param color
     * @return
     */
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
    /*
    We need to rethink this method because the bonus are both resources and dice.
     */
    @Override
    public Resource getBonusOnTower(CardColorEnum color) {
        return null;
    }
}
