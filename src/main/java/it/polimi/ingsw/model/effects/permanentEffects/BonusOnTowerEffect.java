package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This class handles all bonus on towers. Both resources and dices
 */
public class BonusOnTowerEffect extends AbstractPermanentEffect{
    protected CardColorEnum towerColor;
    protected Resource discountOnResource;
    protected int bonusOnDice;
    public BonusOnTowerEffect(CardColorEnum towerColor, Resource resourceDiscount, int bonusOnDice)
    {
        this.towerColor = towerColor;
        this.discountOnResource = resourceDiscount;
        this.bonusOnDice = bonusOnDice;

    }

    public CardColorEnum getTowerColor() {
        return towerColor;
    }
    public Resource getDiscountOnResource() {
        return discountOnResource;
    }

    /**
     * This method checks if there's a bonus linked to that tower. It overrides the method to return the right value
     * If there is, it returns the amount of that bonus.
     * @param color the color of the tower / card
     * @return the bonus, 0 otherwise
     */
    @Override
    public int getBonusOnDice(CardColorEnum color) {
        if(color == towerColor)
           return bonusOnDice;
        return 0;
    }

    /**
     * This method checks if there's a bonus linked to that tower. It overrides the method to return the right value
     * If there is, it returns the amount of that bonus.
     * @param color the color of the tower / card
     * @return the Resource, null if the color is wrong
     */
    @Override
    public Resource getDiscountOnTower(CardColorEnum color) {
        if(color == towerColor)
            return discountOnResource;
        return null;
    }
    public String getShortDescription(){
        String temp = "+"+ bonusOnDice + " On " + towerColor.getCardColor();
        if (discountOnResource != null)
            temp += "-" + discountOnResource.getResourceShortDescript() + " " ;
        return temp;
    }
    public String getDescription(){
        String temp =  "Get a +" + bonusOnDice + " On " + towerColor.getCardColor().toString() + "towers. ";
        if (discountOnResource != null)
            temp += "And -" + discountOnResource;
        return temp;
    }
}
