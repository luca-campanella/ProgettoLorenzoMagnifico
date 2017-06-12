package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.player.Player;

/**
 *
 */
public class CopyAnotherLeaderAbility extends AbstractImmediateLeaderAbility {

    @Override
    public String getAbilityDescription() {
        return "Copy another leader ability.";
    }

    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName) {
        //todo: clientHandlerChoiches, choose a leader effect.
        //setto l'abilitò del leader scelto dal player.

    }
}
