package it.polimi.ingsw.model.leaders.leadersabilities;

import java.util.Optional;

/**
 * This leader ability gives you a bonus on one of your colored family members
 * This ability is usually implemented by "Federico da Montefeltro"
 */
public class BonusOneColoredFamilyMemberLeaderAbility extends AbstractLeaderAbility {

    private int bonusValue;
    private boolean used = false;

    public BonusOneColoredFamilyMemberLeaderAbility(int bonusValue){
        this.bonusValue = bonusValue;
    }


    /**
     * Override of the method to return the correct value of this particular ability
     * Before using this method it should be checked if the bonus was not already used this round
     * by calling the method {@link AbstractOncePerRoundLeaderAbility#wasUsedThisRound()}
     * @return the value of the bonus on the colored family member if not already used, otherwise an empty Optional
     */
    @Override
    public Optional<Integer> getBonusOneColoredFamilyMembers(){
       /* if(used)
            return Optional.empty();*/
        return Optional.of(bonusValue);
    }


    @Override
    public String getAbilityDescription() {
        return "One of your colored Family Members has a value of " + bonusValue + ", regardless of its related dice.";
    }

   /* public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }*/
}