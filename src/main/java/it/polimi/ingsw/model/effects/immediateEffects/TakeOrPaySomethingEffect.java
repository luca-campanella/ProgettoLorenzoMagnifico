package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.utils.Debug;

/**
 * Created by higla on 16/05/2017.
 * This methods gives or let players pay resources.
 */
public class TakeOrPaySomethingEffect implements ImmediateEffectInterface {
    Resource resource;
    public TakeOrPaySomethingEffect(Resource resource){
        this.resource = resource;
    }
    public TakeOrPaySomethingEffect(String resourceType, int value)
    {
        Debug.printVerbose("Hello");
    }
    @Override
    public void applyToPlayer(Player player) {
        giveResourcesToPlayer(player, resource);
    }

    private void giveResourcesToPlayer(Player player, Resource resourceToPlayer){
        //do nothing at the moment.
    }

    public String descriptionOfEffect(){
        return "This method gives to the player " + resource.getResourceAbbreviation();
    }
    public String descriptionShortOfEffect(){
        return resource.getResourceAbbreviation();
    }
}
