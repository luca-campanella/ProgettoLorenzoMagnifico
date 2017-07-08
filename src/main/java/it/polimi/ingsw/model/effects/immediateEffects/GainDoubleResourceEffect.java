package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Handling double resources council gift
 */
public class GainDoubleResourceEffect extends GainResourceEffect implements ImmediateEffectInterface{
    private Resource secondResource;
    public GainDoubleResourceEffect(Resource resource, Resource secondResource){
        super(resource);
        this.secondResource = secondResource;
    }
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName) {
        giveResourcesToPlayer(player, resource);
        giveResourcesToPlayer(player, secondResource);
    }
    public String descriptionShortOfEffect(){
        return (resource.getResourceShortDescript() + secondResource.getResourceShortDescript());
    }
}
