package it.polimi.ingsw.model.leaders.leadersabilities;

import it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.AbstractPermanentLeaderAbility;

/**
 * This in an empty ability, is used in particular in the concatenation of the method {@link AbstractPermanentLeaderAbility#getIfNotUsedThisRound()}
 */
public class EmptyLeaderAbility extends AbstractPermanentLeaderAbility {


    public EmptyLeaderAbility(LeaderAbilityTypeEnum abilityType) {
        super(abilityType);
    }

    @Override
    public String getAbilityDescription() {
        return "No ability";
    }
}
