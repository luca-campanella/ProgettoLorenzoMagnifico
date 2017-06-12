package it.polimi.ingsw.model.leaders.leadersabilities.PermanenteLeaderAbility;

import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility.OncePerRoundResourceLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility.OncePerRoundHarvestLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility.OncePerRoundProductionLeaderAbility;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This class describes how a leader ability should be formed.
 * Each single ability will override a particular method,
 * in order to return the correct value for that ability and not the value corresponding to not having that ability
 */
public abstract class AbstractPermanentLeaderAbility extends AbstractLeaderAbility{


    public AbstractPermanentLeaderAbility() {
        super();
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

    /**
     * This method will usually be overridden by "Ludovico il Moro" ability, in {@link FixedFamilyMembersValueLeaderAbility}
     * @return empty Optional
     */
    public Optional<Integer> getFixedFamilyMembersValue() {
        return Optional.empty();
    }

    /**
     * This method will usually be overridden by "Lucreazia Borgia" ability to return the right bonus value, in {@link BonusNeutralFMLeaderAbility}
     * @return 0
     */
    public int getBonusColoredFamilyMembers() {
        return 0;
    }

    /**
     * This method will usually be overridden by "Federico da Montefeltro" ability, in {@link BonusOneColoredFamilyMemberLeaderAbility}
     * @return empty Optional
     */
    public Optional<Integer> getBonusOneColoredFamilyMembers() {
        return Optional.empty();
    }

    /**
     * Santa Rita's Effect. It tells how many times more we need to give resources to the player
     */
    public int doubleResourcesOnImmediateCardEffectAbility(){return 0;}
    /*
     * This method will be overridden only by abilities that can be activated once per round
     * in the class {@link AbstractOncePerRoundLeaderAbility}
     *
     * This method should be used to understand if a leader was already used in that round and his ability cannot be used again
     * @return false
     */
    /*public boolean wasUsedThisRound() {
        return false;
    }*/

    /*
     * This method will be overridden only by abilities that can be activated once per round
     * in the class {@link AbstractOncePerRoundLeaderAbility}
     *
     * This method should be used to set the used parameter once the leader was used this round
     * and should be called at the end of each round to set false
     * @param used
     */
    /*public void setUsedThisRound(boolean used) {
        ;//do nothing the method will bew overridden
    }*/

    /*
     * This method will be overridden only by abilities that can be activated once per round
     * in the class {@link AbstractOncePerRoundLeaderAbility}
     *
     * This method should be used to get the ability only if was not already used that round,
     * othewrise getting an empty Ability {@link EmptyLeaderAbility}
     *
     * --Example of code usage--
     * <code>
     *     leaderCard.getAbility().getIfNotUsedThisRound().get
     * </code>
     * @return an EmptyLeaderAbility
     */
    /*public AbstractPermanentLeaderAbility getIfNotUsedThisRound() {
        return new EmptyLeaderAbility();
    }*/
}
