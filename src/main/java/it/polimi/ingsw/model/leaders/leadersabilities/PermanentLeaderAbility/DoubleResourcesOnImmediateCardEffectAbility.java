package it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility;

/**
 * this class handles Santa Rita's ability.
 */
public class DoubleResourcesOnImmediateCardEffectAbility extends AbstractPermanentLeaderAbility {
    int doubleResource;

    public DoubleResourcesOnImmediateCardEffectAbility(int doubleResource) {
        super();
        this.doubleResource = doubleResource;
    }


    @Override
    public String getAbilityDescription() {
        return "Each time you receive wood, stone, coins, or servants as an immediate effect from Development Cards (not from an action space), you receive the resources twice.";
    }

    @Override
    public int doubleResourcesOnImmediateEffects(){return doubleResource;}
}
