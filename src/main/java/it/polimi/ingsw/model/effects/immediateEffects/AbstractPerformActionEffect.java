package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;

/**
 * Created by higla on 23/05/2017.
 */
public abstract class AbstractPerformActionEffect implements ImmediateEffectInterface{
    int diceValue;

    public abstract void applyToPlayer(Player player);


}
