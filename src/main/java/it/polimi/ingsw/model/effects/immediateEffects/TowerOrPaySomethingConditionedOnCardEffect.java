package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class TowerOrPaySomethingConditionedOnCardEffect extends TakeOrPaySomethingEffect {
    CardColorEnum color;
    public TowerOrPaySomethingConditionedOnCardEffect(Resource resource, CardColorEnum color){
        super(resource);
        this.color = color;
    }

    @Override
    /*
    This method gives you resources for each card of a certain type you have
     */
    public void applyToPlayer(Player player) {
        int numberOfColoredCards = player.getNumberOfColoredCard(color);
        int i;
        for(i = 0; i<numberOfColoredCards; i++)
            super.applyToPlayer(player);
    }
    public String descriptionOfEffect(){
        return "This method gives to the player a dice that he can use and place somewher" + resource.getResourceAbbreviation();
    }
    public String descriptionShortOfEffect(){
        return resource.getResourceAbbreviation();
    }
}
