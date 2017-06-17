package it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility;

import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;

/**
 * This leader ability tells that you gain n faith point more when you decide to support the vatican
 * This ability is usually implemented by "Sisto IV" leader
 */
public class GainFaithPointsSupportingChurchLeaderAbility extends AbstractPermanentLeaderAbility {
    private int additionalFaithPoints;

    public GainFaithPointsSupportingChurchLeaderAbility(LeaderAbilityTypeEnum leaderAbilityType, int additionalFaithPoints) {
        super(leaderAbilityType);
        this.additionalFaithPoints = additionalFaithPoints;
    }

    /**
     * This method ovverrides the corresponding method inside {@link AbstractPermanentLeaderAbility }
     * to return the correct value that this effect implements
     * @return the bonus on faith points
     */
    @Override
    public int getAdditionalFaithPointsForSupportingChurch() {
        return additionalFaithPoints;
    }

    @Override
    public String getAbilityDescription() {
        return "You gain " + additionalFaithPoints + " additional Victory Points when you support the Church " +
                "in a Vatican Report phase.";
    }
}
