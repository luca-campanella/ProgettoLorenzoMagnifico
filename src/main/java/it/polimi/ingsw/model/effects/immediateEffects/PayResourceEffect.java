package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This class is generally used for the cost
 */
public class PayResourceEffect implements ImmediateEffectInterface {
    Resource cost;

    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName) {
        giveResourcesToPlayer(player, cost);
    }

    private void giveResourcesToPlayer(Player player, Resource resource){
        player.addResource(resource);
    }

    public String descriptionOfEffect(){
        return "This Effect let you pay " + cost.getResourceShortDescript();
    }
    public String descriptionShortOfEffect(){
        return cost.getResourceShortDescript();
    }
}

