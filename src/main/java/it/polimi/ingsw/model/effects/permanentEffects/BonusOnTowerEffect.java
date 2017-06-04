package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

/**
 * This class handles all bonus on towers. Both resources and dices
 */
public class BonusOnTowerEffect extends AbstractPermanentEffect{
    protected CardColorEnum towerColor;
    protected Resource discountOnResource;
    protected int discountOnDice;
    public BonusOnTowerEffect(CardColorEnum towerColor, Resource resourceDiscount, int diceDiscount)
    {
        this.towerColor = towerColor;
        this.discountOnResource = resourceDiscount;
        this.discountOnDice = diceDiscount;

    }

    public CardColorEnum getTowerColor() {
        return towerColor;
    }
    public Resource getDiscountOnResource() {
        return discountOnResource;
    }
    public int getDiscountOnDice() {
        return discountOnDice;
    }

    /**
     This method checks if there's a bonus linked to that tower.
    If there is, it returns the amount of that bonus.
     */
    @Override
    public Resource getDiscountOnTower(CardColorEnum color) {
        if(color == towerColor)
            return discountOnResource;
        return null;
    }
    public String getShortDescription(){
        return "+"+ discountOnDice + " On " + towerColor.getCardColor() + "-" + discountOnResource.getResourceShortDescript() + " " ;
    }
    public String getDescription(){
        return "Get a +" + discountOnDice + " On " + towerColor.getCardColor().toString() + "towers. And -" + discountOnResource;
    }
}
