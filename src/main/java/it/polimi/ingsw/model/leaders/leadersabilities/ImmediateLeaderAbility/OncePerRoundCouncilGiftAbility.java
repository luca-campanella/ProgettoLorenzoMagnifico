package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;
import it.polimi.ingsw.model.player.Player;

/**
 * This class handles the leader's ability that gives the player the possibility to use
 * a council gift each round.
 */
public class OncePerRoundCouncilGiftAbility extends AbstractImmediateLeaderAbility {
    int numberOfDifferentCouncilGift;

    public OncePerRoundCouncilGiftAbility(LeaderAbilityTypeEnum leaderAbilityType, int numberOfDifferentCouncilGift){
        super(leaderAbilityType);
        this.numberOfDifferentCouncilGift = numberOfDifferentCouncilGift;
    }

    @Override
    public String getAbilityDescription() {
        return null;
    }

    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName) {
        //todo give council gift to a player;
    }
}
