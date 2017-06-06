package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This effect allows you to take something if you have some cards
 */
public class GainResourceConditionedOnCardEffect extends GainResourceEffect {
    CardColorEnum colorConditionedOnCardEffect;
    //instead of 2 points victory for 1 card, we could have 1 point victory for 2 cards
    //it could also be 2x3 cards...
    int numberOfCards;
    public GainResourceConditionedOnCardEffect(Resource resource, CardColorEnum color, int numberOfCards){
        super(resource);
        this.colorConditionedOnCardEffect = color;
    }

    /**
      *  This method gives you resources for each card of a certain type you have
     */
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName) {
        //first you get the number of sameColoredCards
        int numberOfColoredCards = player.getNumberOfColoredCard(colorConditionedOnCardEffect);
        int i;
        for(i = 0; i<numberOfColoredCards; i += numberOfCards) {
            super.applyToPlayer(player, choicesHandlerInterface, cardName);
        }
    }
    public String descriptionOfEffect(){
        return "This effect gives you some resources " + resource.getResourceShortDescript() + " if you have " + numberOfCards + " of this " + colorConditionedOnCardEffect;
    }
    public String descriptionShortOfEffect(){
        return resource.getResourceShortDescript() + " x " + numberOfCards + " " + colorConditionedOnCardEffect.getCardColor();
    }
}
