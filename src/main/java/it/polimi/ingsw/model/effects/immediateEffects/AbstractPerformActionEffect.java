package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;

/**
 * This class is the abstract perform harvest or build effects.
 */
public abstract class AbstractPerformActionEffect implements ImmediateEffectInterface{
    int diceValue;

    public abstract void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface);


}
