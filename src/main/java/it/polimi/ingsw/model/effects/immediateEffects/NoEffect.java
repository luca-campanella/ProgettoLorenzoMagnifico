package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;

/**
 * Created by higla on 17/05/2017.
 */
public class NoEffect implements  ImmediateEffectInterface {

    public void applyToPlayer(Player player){
        //This method doesn't do anything
        ;
    }
    public String descriptionOfEffect()
    {
        return "This effect hasn't effect ";
    }
    public String descriptionShortOfEffect(){
        return "No  ";
    }
}
