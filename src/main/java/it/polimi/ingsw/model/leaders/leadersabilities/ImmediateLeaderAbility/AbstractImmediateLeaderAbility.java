package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.player.Player;

/**
 * this class handles all immediate leaders effect
 */
public abstract class AbstractImmediateLeaderAbility extends AbstractLeaderAbility{

    public abstract void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName);

}
