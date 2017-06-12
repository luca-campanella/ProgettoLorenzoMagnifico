package it.polimi.ingsw.model.leaders.leadersabilities.PermanenteLeaderAbility;

import it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility.AbstractOncePerRoundLeaderAbility;

/**
 * This leader ability gives you a bonus on all your colored family members
 * This ability is usually implemented by "Lucreazia Borgia"
 */
public class BonusColoredFamilyMembersLeaderAbility extends AbstractPermanentLeaderAbility {

    private int bonusValue;

    public BonusColoredFamilyMembersLeaderAbility(int bonusValue){
        this.bonusValue = bonusValue;
    }


    /**
     * Override of the method to return the correct value of this particular ability
     * Before using this method it should be checked if the bonus was not already used this round
     * by calling the method {@link AbstractOncePerRoundLeaderAbility#wasUsedThisRound()}
     * @return the value of the bonus on the neutral / uncolored family member
     */
    @Override
    public int getBonusColoredFamilyMembers(){
        return bonusValue;
    }


    @Override
    public String getAbilityDescription() {
        return "Your colored Family Members have a bonus of " + bonusValue + " on their value. (You can increase their value by spending servants or if you have Character Cards with this effect.)";
    }
}