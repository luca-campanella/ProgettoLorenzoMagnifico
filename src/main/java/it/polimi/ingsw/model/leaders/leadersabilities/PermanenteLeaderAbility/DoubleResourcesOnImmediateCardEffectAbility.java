package it.polimi.ingsw.model.leaders.leadersabilities.PermanenteLeaderAbility;

/**
 * this class handles Santa Rita's ability.
 */
public class DoubleResourcesOnImmediateCardEffectAbility extends AbstractPermanentLeaderAbility {
    int doubleResource;
    public int doubleResourcesOnImmediateCardEffectAbility(){return doubleResource;}


    @Override
    public String getAbilityDescription() {
        return "Each time you receive wood, stone, coins, or servants as an immediate effect from Development Cards (not from an action space), you receive the resources twice.";
    }

    public DoubleResourcesOnImmediateCardEffectAbility(int doubleResource) {
        this.doubleResource = doubleResource;
    }
}
