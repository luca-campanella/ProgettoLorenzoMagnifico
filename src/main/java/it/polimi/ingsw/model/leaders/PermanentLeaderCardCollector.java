package it.polimi.ingsw.model.leaders;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.AbstractPermanentLeaderAbility;
import it.polimi.ingsw.model.resource.Resource;

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
     * Only leaders with {@link it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum == PERMANENT}
     * should be added
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
        for(LeaderCard leaderIter : permanentLeaders)
            if(((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).canPlaceFamilyMemberInOccupiedActionSpace())
                return true;
        return false;
    }

    /**
     * This method returns if the player has not to spend to place in an occupied tower
     * This method will usually return true if "Filippo Bunelleschi" is in the collector,
     * Effect implemented in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.NotToSpendForOccupiedTowerLeaderAbility}
     * @return true if any leader has this ability
     */
    public boolean hasNotToSpendForOccupiedTower() {
        for(LeaderCard leaderIter : permanentLeaders)
            if(((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).hasNotToSpendForOccupiedTower())
                return true;
        return false;
    }

    /**
     * This method returns the bonus on the Neutral family member dictated by leaders
     * This method will usually return a value != 0 if "Sigismondo Malatesta" is in the Collector,
     * Effect implemented in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.BonusNeutralFMLeaderAbility}
     * @return the bonus on the neutral family member if present, 0 otherwise
     */
    public int getBonusNeutralFM(){
        int bonus = 0;
        for(LeaderCard leaderIter : permanentLeaders)
            bonus += ((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).getBonusNeutralFM();

        return bonus;
    }

    /**
     * This method returns the fixed value that family members assume if there is a leader with this ability
     * This method will usually return a not empty Optional if "Ludovico il Moro" is in the Collector,
     * Effect implemented in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.FixedFamilyMembersValueLeaderAbility}
     * @return The Optional with the value inside, Optional.empty() otherwise
     */
    public Optional<Integer> getFixedFamilyMembersValue() {
        Optional<Integer> result;
        for(LeaderCard leaderIter : permanentLeaders) {
            result =((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).getFixedFamilyMembersValue();
            if(result.isPresent())
                return result;
        }

        return Optional.empty();
    }

    /**
     * This method returns the bonus on the colored family members dictated by leaders
     * This method will usually return a value != 0 if "Lucreazia Borgia" is in the Collector,
     * Effect implemented in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.BonusColoredFamilyMembersLeaderAbility}
     * @return the bonus on the colored family members if present, 0 otherwise
     */
    public int getBonusColoredFamilyMembers() {
        int bonus = 0;
        for(LeaderCard leaderIter : permanentLeaders)
            bonus += ((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).getBonusColoredFamilyMembers();

        return bonus;
    }

    /**
     * This method return the number of additional faith point that should be added to the player when he's supporting the church
     * This method will usually return != 0 if "Sisto IV" is present in the Collector,
     * Effect implemented in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.GainFaithPointsSupportingChurchLeaderAbility}
     * @return the bonus on faith points if present, 0 otherwise
     */
    public int getAdditionalFaithPointsForSupportingChurch() {
        int bonus = 0;
        for(LeaderCard leaderIter : permanentLeaders)
            bonus += ((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).getAdditionalFaithPointsForSupportingChurch();

        return bonus;
    }

    /**
     * This method return true if the player doesn't need to have a certain amount
     * of military points to pick territory cards
     * This method will usually return true if "Cesare Borgia" is in the Collector,
     * Effect implemented in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.NoMilitaryPointsNeededForTerritoryCardsLeaderAbility}
     * @return true if the player doesn't need to have a certain amount of military points to pick territory cards
     */
    public boolean noMilitaryPointsNeededForTerritoryCards() {
        for(LeaderCard leaderIter : permanentLeaders)
            if(((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).noMilitaryPointsNeededForTerritoryCards())
                return true;
        return false;
    }

    /**
     * This method return how many times more the resources should be added to the player when taking a card of that type
     * Santa Rita's Effect. It tells how many times more we need to give resources to the player
     * @param cardColor the color of the card to check if there's the bonus on
     * @return how many times more than 1 the resources should be added
     */
    public int getMoreTimesResourcesOnImmediateEffects(CardColorEnum cardColor){
        int bonus = 0;
        for(LeaderCard leaderIter : permanentLeaders)
            bonus += ((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).getMoreTimesResourcesOnImmediateEffects(cardColor);

        return bonus;
    }

    /**
     * This method returns the resource discount when buying a card of certain type
     * This method will usually return a not empty list if "Pico della Mirandola" is in the Collector,
     * Effect implemented in {@link it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.DiscountOnCardCostLeaderAbility}
     * @param cardColor the color of the card we should check if there's a discount on
     * @return The list of discounts, empty list otherwise
     */
    public List<Resource> getDiscountOnCardCost(CardColorEnum cardColor) {
        LinkedList<Resource> discount = new LinkedList<>();
        Resource res;
        for(LeaderCard leaderIter : permanentLeaders) {
            res = ((AbstractPermanentLeaderAbility) (leaderIter.getAbility())).getDiscountOnCardCost(cardColor);
            if(res != null)
                discount.add(res);
        }
        return discount;
    }

    public List<LeaderCard> getPermanentLeaders() {
        return permanentLeaders;
    }
}
