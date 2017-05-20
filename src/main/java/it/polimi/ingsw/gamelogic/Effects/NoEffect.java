package it.polimi.ingsw.gamelogic.Effects;

import it.polimi.ingsw.gamelogic.Player.Player;

/**
 * Created by higla on 17/05/2017.
 */
public class NoEffect implements EffectInterface {

    public void applyToPlayer(Player player){
        //This method doesn't do anything
        ;
    }
    public String descriptionOfEffect()
    {
        return "This effect hasn't effect ";
    }
}
