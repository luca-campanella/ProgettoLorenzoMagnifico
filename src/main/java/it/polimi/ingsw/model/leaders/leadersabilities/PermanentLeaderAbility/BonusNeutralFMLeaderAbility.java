package it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility;

import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;

/**
 * This leader ability gives you a bonus on your neutral / uncolored family member
 * This ability is usually implemented by "Sigismondo Malatesta"
 */
public class BonusNeutralFMLeaderAbility extends AbstractPermanentLeaderAbility {

    private int bonusValue;

    public BonusNeutralFMLeaderAbility(LeaderAbilityTypeEnum leaderAbilityType, int bonusValue){
        super(leaderAbilityType);
        this.bonusValue = bonusValue;
    }

    /**
     * Override of the method to return the correct value of this particular ability
     * @return the value of the bonus on the neutral / uncolored family member
     */
    @Override
    public int getBonusNeutralFM(){
        return bonusValue;
    }


    @Override
    public String getAbilityDescription() {
        return "Your uncolored Family Member has a bonus of " + bonusValue + " on its value. (You can increase its value by spending servants or if you have Character Cards with this effect.)";
    }
}
