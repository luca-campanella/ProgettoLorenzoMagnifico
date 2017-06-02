package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This effect allows you to take something if you have some cards
 */
public class TakeOrPaySomethingConditionedOnCardEffect extends TakeOrPaySomethingEffect {
    CardColorEnum colorConditionedOnCardEffect;
    int numberOfCards;
    public TakeOrPaySomethingConditionedOnCardEffect(Resource resource, CardColorEnum color, int numberOfCards){
        super(resource);
        this.colorConditionedOnCardEffect = color;
    }

    /**
      *  This method gives you resources for each card of a certain type you have
     */
    @Override
    public void applyToPlayer(Player player) {
        int numberOfColoredCards = player.getNumberOfColoredCard(colorConditionedOnCardEffect);
        int i;
        for(i = 0; i<numberOfColoredCards;)
            super.applyToPlayer(player);
            i += numberOfCards;
    }
    public String descriptionOfEffect(){
        return "This method gives to the player a dice that he can use and place somewher" + resource.getResourceShortDescript();
    }
    public String descriptionShortOfEffect(){
        return resource.getResourceShortDescript() + " x " + numberOfCards + " " + colorConditionedOnCardEffect.getCardColor();
    }
}
