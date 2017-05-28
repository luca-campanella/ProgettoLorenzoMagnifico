package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class TakeOrPaySomethingConditionedOnCardEffect extends TakeOrPaySomethingEffect {
    CardColorEnum colorConditionedOnCardEffect;
    int numberOfCards;
    public TakeOrPaySomethingConditionedOnCardEffect(Resource resource, CardColorEnum color, int numberOfCards){
        super(resource);
        this.colorConditionedOnCardEffect = color;
    }

    @Override
    /*
    This method gives you resources for each card of a certain type you have
     */
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
        return resource.getResourceShortDescript() + " " + numberOfCards;
    }
}
