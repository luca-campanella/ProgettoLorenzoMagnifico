package it.polimi.ingsw.model.leaders.leadersabilities;

import java.util.Optional;

/**
 * This in an empty ability, is used in particular in the concatenation of the method {@link AbstractLeaderAbility#getIfNotUsedThisRound()}
 */
public class EmptyLeaderAbility extends AbstractLeaderAbility {


    @Override
    public String getAbilityDescription() {
        return "No ability";
    }
}
