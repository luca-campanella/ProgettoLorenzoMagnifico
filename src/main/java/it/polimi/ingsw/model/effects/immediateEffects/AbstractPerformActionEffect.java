package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;

/**
 * This class is the abstract perform action effect.
 */
public abstract class AbstractPerformActionEffect implements ImmediateEffectInterface{
    int diceValue;

    public abstract void applyToPlayer(Player player);


}
