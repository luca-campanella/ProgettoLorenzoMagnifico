package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;
import it.polimi.ingsw.model.player.Player;

/**
 *
 */
public class CopyAnotherLeaderAbility extends AbstractImmediateLeaderAbility {

    public CopyAnotherLeaderAbility(LeaderAbilityTypeEnum leaderAbilityType) {
        super(leaderAbilityType);
    }

    @Override
    public String getAbilityDescription() {
        return "Copy the ability of another Leader Card already played by another player. Once you " +
                "decide the ability to copy, it can’t be changed.";
    }

    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName) {
        //todo: clientHandlerChoiches, choose a leader effect.
        //setto l'abilitò del leader scelto dal player.

    }
}
