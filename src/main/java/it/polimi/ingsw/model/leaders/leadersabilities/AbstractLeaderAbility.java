package it.polimi.ingsw.model.leaders.leadersabilities;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This class describes how a leader ability should be formed.
 * Each single ability will override a particular method,
 * in order to return the correct value for that ability and not the value corresponding to not having that ability
 */
public abstract class AbstractLeaderAbility {


    public AbstractLeaderAbility() {
        super();
    }

    /**
     * this method will bew overridden by the ability of all Leaders having a once per round bonus which is a resource,
     * if no LeaderAbility overrides this method by default it returns null
     * overridden in {@link OncePerRoundBonusLeaderAbility}
     * @return null
     */
    public ArrayList<Resource> getOncePerRoundBonus() {
        return null;
    }

    /**
     * This method will usually be overridden by "Francesco Sforza" ability, in {@link OncePerRoundHarvestLeaderAbility}
     * If the optimal is empty no leader has this ability and the action should not be performed
     * @return empty Optional
     */
    public Optional<Integer> getOncePerRoundHarvestDiceValue() {
        return Optional.empty();
    }

    /**
     * This method will usually be overridden by "Leonardo Da Vinci" ability, in {@link OncePerRoundProductionLeaderAbility}
     * If the optimal is empty no leader has this ability and the action should not be performed
     * @return empty Optional
     */
    public Optional<Integer> getOncePerRoundProductionDiceValue() {
        return Optional.empty();
    }

    /**
     * This method will usually be overridden by "Ludovico Ariosto" ability, in {@link CanPlaceFMInOccupiedASLeaderAbility}
     * @return false
     */
    public boolean canPlaceFamilyMemberInOccupiedActionSpace() {
        return false;
    }

    /**
     * This method will usually be overridden by "Filippo Bunelleschi" ability, in {@link NotToSpendForOccupiedTowerLeaderAbility}
     * @return false
     */
    public boolean hasNotToSpendForOccupiedTower() {
        return false;
    }

    /**
     * This method will usually be overridden by "Sigismondo Malatesta" ability, in {@link BonusNeutralFMLeaderAbility}
     * @return 0
     */
    public int getBonusNeutralFM(){
        return 0;
    }


    public abstract String getAbilityDescription();
}
