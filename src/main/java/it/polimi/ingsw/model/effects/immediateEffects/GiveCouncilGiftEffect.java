package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;

/**
 * Created by higla on 20/05/2017.
 */
public class GiveCouncilGiftEffect implements ImmediateEffectInterface {
    public void applyToPlayer(Player player)
    {
        ;
    }
    public String descriptionOfEffect(){
        return "Gives a Council Gift to a player.";
    }
    public String descriptionShortOfEffect(){return "Gift";}
}
