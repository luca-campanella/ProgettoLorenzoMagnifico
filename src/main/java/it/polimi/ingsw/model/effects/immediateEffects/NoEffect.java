package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;

/**
 * This effect hasn't effect
 * */
public class NoEffect implements  ImmediateEffectInterface {

    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName){
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
