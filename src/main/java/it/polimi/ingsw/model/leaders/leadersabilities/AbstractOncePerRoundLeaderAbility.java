package it.polimi.ingsw.model.leaders.leadersabilities;

/**
 * For now this class is not used, still to decide how to implement once per round bonuses. my suggestion: two different arrays in player
 */
public abstract class AbstractOncePerRoundLeaderAbility extends AbstractLeaderAbility {
    private boolean wasUsedThisRound = false;

    @Override
    public abstract String getAbilityDescription();

    /**
     * This method overrides the upper class method, once for all once-per-round abilities that will extend this class
     *
     * This method should be used to understand if a leader was already used in that round and his ability cannot be used again
     * @return true if the leader was already used this round
     */
    //@Override
    public boolean wasUsedThisRound() {
        return wasUsedThisRound;
    }

    /**
     * This method overrides the upper class method, once for all once-per-round abilities that will extend this class
     *
     * This method should be used to set the used parameter once the leader was used this round
     * and should be called at the end of each round to set false
     * @param used
     */
    //@Override
    public void setUsedThisRound(boolean used) {
        this.wasUsedThisRound = used;
    }
}
