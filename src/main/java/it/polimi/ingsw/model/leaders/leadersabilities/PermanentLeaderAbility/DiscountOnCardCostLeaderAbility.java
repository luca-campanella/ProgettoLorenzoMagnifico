package it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * "When you take a ccard of some type, you get a discount of a certain resource (if the card you are taking has
 *  this kind of resource in its cost.) This is not a discount on the coins you must spend if you take a card from
 *  a Tower that’s already occupied."
 *  This ability is usually implemented by "Pico della Mirandola"
 */
public class DiscountOnCardCostLeaderAbility extends AbstractPermanentLeaderAbility {

    /**
     * The color of the card the discount is valid for
     */
    private CardColorEnum cardColor;

    /**
     * The discounted resource on the card cost (it's positive)
     */
    private Resource discount;

    public DiscountOnCardCostLeaderAbility(LeaderAbilityTypeEnum leaderAbilityType, CardColorEnum cardColor, Resource discount) {
        super(leaderAbilityType);
        this.cardColor = cardColor;
        this.discount = discount;
    }

    /**
     * Override of the method to return the correct value, in this case the resource discounted
     * @param cardColor the color of the card we should check if there's a discount on
     * @return the discount, null if the card has a different color from the one of the effect
     */
    @Override
    public Resource getDiscountOnCardCost(CardColorEnum cardColor) {
        if(this.cardColor != cardColor)
            return null;
        return discount;
    }

    @Override
    public String getAbilityDescription() {
        return "When you take a " + cardColor.getFullDescription() + ", you get a discount of " +
                discount.getResourceFullDescript() + " (if the card you are taking has " +
                "this kind of resource in its cost.) This is not a discount on the coins you must spend if you take a " +
                "card from a Tower that’s already occupied.";
    }
}
