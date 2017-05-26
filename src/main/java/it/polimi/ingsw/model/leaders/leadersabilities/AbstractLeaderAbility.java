package it.polimi.ingsw.model.leaders.leadersabilities;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by campus on 27/05/2017.
 */
public class AbstractLeaderAbility {

    private String abilityDescription;

    public AbstractLeaderAbility(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }

    /**
     * this method will bew overridden by all Leaders having a once per round bonus which is a resource, if no leader overrides this method by default it returns null
     * @return null
     */
    public ArrayList<Resource> getOncePerRoundBonus() {
        return null;
    }

    /**
     * This method will usually be overridden by "Francesco Sforza" ability
     * @return empty Optional
     */
    public Optional<Integer> getOncePerRoundHarvestDiceValue() {
        return Optional.empty();
    }

    /**
     * This method will usually be overridden by "Ludovico Ariosto" ability
     * @return false
     */
    public boolean canPlaceFamilyMemberInOccupiedActionSpace() {
        return false;
    }

    /**
     * This method will usually be overridden by "Filippo Bunelleschi" ability
     * @return
     */
    public boolean hasNotToSpendForOccupiedTower() {
        return false;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    protected void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }
}
