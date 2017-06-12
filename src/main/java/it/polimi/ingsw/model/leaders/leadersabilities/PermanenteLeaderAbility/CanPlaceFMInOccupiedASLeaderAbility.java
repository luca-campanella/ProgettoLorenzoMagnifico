package it.polimi.ingsw.model.leaders.leadersabilities.PermanenteLeaderAbility;

/**
 * this ability lets the player place a family member in an already occupied action space
 * This ability usually be implemented by "Ludovico Ariosto"
 */
public class CanPlaceFMInOccupiedASLeaderAbility extends AbstractPermanentLeaderAbility {

    public CanPlaceFMInOccupiedASLeaderAbility() {
        super();
    }

    /**
     * Override of the method to return the correct value of the effect
     * @return true
     */
    @Override
    public boolean canPlaceFamilyMemberInOccupiedActionSpace() {
        return true;
    }

    @Override
    public String getAbilityDescription() {
        return "You can place your Family Members in occupied action spaces.";
    }

}
