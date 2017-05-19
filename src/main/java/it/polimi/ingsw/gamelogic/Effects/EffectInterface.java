package it.polimi.ingsw.gamelogic.Effects;

import it.polimi.ingsw.gamelogic.Player.Player;

/**
 * Created by higla on 16/05/2017.
 */
public interface EffectInterface {
    /**
     * this method is the method that all effects must implements
     */
    abstract public void applyToPlayer(Player player);

    /**
     * when someone invokes this method, gets a description of what the method does
     */
    abstract public String descriptionOfEffect();

}

