package it.polimi.ingsw.gamelogic.Effects;

import it.polimi.ingsw.gamelogic.Player.Player;
import it.polimi.ingsw.gamelogic.Resource.Resource;

/**
 * Created by higla on 16/05/2017.
 * This methods gives or let players pay resources.
 */
public class TakeOrPaySomethingEffect implements EffectInterface {
    Resource resource;

    TakeOrPaySomethingEffect(String resourceType, int value)
    {
        //Come inizializzare il tipo di risorsa?
    }
    @Override
    public void applyToPlayer(Player player) {
        giveResourcesToPlayer(player, resource);
    }

    private void giveResourcesToPlayer(Player player, Resource resources){
        //do nothing at the moment.
    }

}
