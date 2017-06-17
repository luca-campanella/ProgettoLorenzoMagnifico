package it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility;

import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;

import java.util.Optional;

/**
 * All the family members acquire the specified value
 * This ability is usually implemented by "Ludovico il Moro" leader card
 */
public class FixedFamilyMembersValueLeaderAbility extends AbstractPermanentLeaderAbility {

    /**
     * the fixed value all the colored family members acquire
     */
    private int diceValue;

    public FixedFamilyMembersValueLeaderAbility(LeaderAbilityTypeEnum leaderAbilityType, int diceValue) {
        super(leaderAbilityType);
        this.diceValue = diceValue;
    }

    @Override
    public String getAbilityDescription() {
        return "Your colored Family Members have a value of " + diceValue + ", regardless of their related dice. (You can increase their value by spending servants or if you have Character Cards with this effect.)";
    }

    /**
     * Override of the method to return the correct value of this particular ability
     * @return Optional with dice value inside
     */
    public Optional<Integer> getFixedFamilyMembersValue() {
        return Optional.of(diceValue);
    }
}
