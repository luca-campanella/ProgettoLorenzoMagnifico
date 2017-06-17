package it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility;

import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;

/**
 * "You don’t need to satisfy the Military Points requirement when you take Territory Cards."
 * This leader ability is usually implemented by "Cesare Borgia"
 */
public class NoMilitaryPointsNeededForTerritoryCardsLeaderAbility extends AbstractPermanentLeaderAbility {

    public NoMilitaryPointsNeededForTerritoryCardsLeaderAbility(LeaderAbilityTypeEnum leaderAbilityType){
        super(leaderAbilityType);
    }

    /**
     * Override to return the correct value
     * @return true
     */
    @Override
    public boolean noMilitaryPointsNeededForTerritoryCards() {
        return true;
    }

    @Override
    public String getAbilityDescription() {
        return "You don’t need to satisfy the Military Points requirement when you take Territory Cards.";
    }
}
