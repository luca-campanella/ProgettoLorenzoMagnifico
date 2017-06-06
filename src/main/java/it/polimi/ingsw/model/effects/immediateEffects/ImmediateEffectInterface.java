package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;

/**
 * Created by higla on 16/05/2017.
 */
public interface ImmediateEffectInterface {


    /**
     * this method is the method that all effects must implements
     */
    abstract public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName);

    /**
     * when someone invokes this method, gets a description of what the method does
     */
    abstract public String descriptionOfEffect();
    abstract public String descriptionShortOfEffect();


}
