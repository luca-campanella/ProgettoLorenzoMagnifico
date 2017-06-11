package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This methods gives or let players pay resources.
 */
public class GainResourceEffect implements ImmediateEffectInterface {
    Resource resource;
    public GainResourceEffect(Resource resource){
        this.resource = resource;
    }

    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName) {
        giveResourcesToPlayer(player, resource);
    }

    private void giveResourcesToPlayer(Player player, Resource resource){
        player.addResource(resource);
    }

    public String descriptionOfEffect(){
        return "This method gives to the player " + resource.getResourceShortDescript();
    }
    public String descriptionShortOfEffect(){
        return resource.getResourceShortDescript();
    }


}
