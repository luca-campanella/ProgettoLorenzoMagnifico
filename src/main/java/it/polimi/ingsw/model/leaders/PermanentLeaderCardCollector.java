package it.polimi.ingsw.model.leaders;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the leaders that have a permanent ability that should be checked during game actions
 */
public class PermanentLeaderCardCollector {
    private List<LeaderCard> permanentLeaders;

    public PermanentLeaderCardCollector(){
        permanentLeaders = new LinkedList<LeaderCard>();
    }

    /**
     * This method adds one card to the collector
     * @param leader the card to be added
     */
    public void addLeaderCard(LeaderCard leader) {
        permanentLeaders.add(leader);
    }

    /**
     * This method returns if the player can place a family member on already occupied action spaces
     * This method will usually return true if "Ludovico Ariosto" is in the Collector,
     * Effect implemented in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.CanPlaceFMInOccupiedASLeaderAbility}
     * @return true if any leader has this ability
     */
    public boolean canPlaceFamilyMemberInOccupiedActionSpace() {
        /*for(LeaderCard leaderIter : permanentLeaders)
            if(leaderIter.getAbility())*/
        return false;
    }

    /**
     * This method will usually be overridden by "Filippo Bunelleschi" ability, in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.NotToSpendForOccupiedTowerLeaderAbility}
     * @return false
     */
    public boolean hasNotToSpendForOccupiedTower() {
        return false;
    }

    /**
     * This method will usually be overridden by "Sigismondo Malatesta" ability, in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.BonusNeutralFMLeaderAbility}
     * @return 0
     */
    public int getBonusNeutralFM(){
        return 0;
    }

    /**
     * This method will usually be overridden by "Ludovico il Moro" ability, in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.FixedFamilyMembersValueLeaderAbility}
     * @return empty Optional
     */
    public Optional<Integer> getFixedFamilyMembersValue() {
        return Optional.empty();
    }

    /**
     * This method will usually be overridden by "Lucreazia Borgia" ability to return the right bonus value, in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.BonusNeutralFMLeaderAbility}
     * @return 0
     */
    public int getBonusColoredFamilyMembers() {
        return 0;
    }

    /**
     * This method will usually be overridden by "Federico da Montefeltro" ability, in {@link it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility.OncePerRoundBonusOneColoredFamilyMemberLeaderAbility}
     * @return empty Optional
     */
    public Optional<Integer> getBonusOneColoredFamilyMembers() {
        return Optional.empty();
    }

    /**
     * Santa Rita's Effect. It tells how many times more we need to give resources to the player
     */
    public int doubleResourcesOnImmediateCardEffectAbility(){return 0;}
}
