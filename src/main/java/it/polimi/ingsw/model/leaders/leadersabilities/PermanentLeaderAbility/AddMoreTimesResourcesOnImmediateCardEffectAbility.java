package it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility;

import it.polimi.ingsw.model.board.CardColorEnum;

/**
 * Each time you receive wood, stone, coins, or servants as an immediate effect from Development Cards
 * (not from an action space), you receive the resources twice.
 * This effect is usually implemented by "Santa Rita" leader card
 */
public class AddMoreTimesResourcesOnImmediateCardEffectAbility extends AbstractPermanentLeaderAbility {
    int timesToAddMore;
    CardColorEnum cardColor;

    public AddMoreTimesResourcesOnImmediateCardEffectAbility(CardColorEnum cardColor, int timesToAddMore) {
        super();
        this.timesToAddMore = timesToAddMore;
        this.cardColor = cardColor;
    }


    @Override
    public String getAbilityDescription() {
        return "Each time you receive wood, stone, coins, or servants as an immediate effect from Development Cards (not from an action space), you receive the resources twice.";
    }

    @Override
    public int getMoreTimesResourcesOnImmediateEffects(CardColorEnum cardColor){
        if(cardColor != this.cardColor)
            return 0;
        return timesToAddMore;
    }
}
