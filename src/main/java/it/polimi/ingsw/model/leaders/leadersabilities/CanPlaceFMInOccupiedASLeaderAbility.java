package it.polimi.ingsw.model.leaders.leadersabilities;

/**
 * this ability lets the player place a family member in an already occupied action space
 * This ability usually be implemented by "Ludovico Ariosto"
 */
public class CanPlaceFMInOccupiedASLeaderAbility extends AbstractLeaderAbility {

    public CanPlaceFMInOccupiedASLeaderAbility() {
        super("You can place your Family Members in occupied action spaces.");
    }

    /**
     * Override of the method to return the correct value of the effect
     * @return true
     */
    @Override
    public boolean canPlaceFamilyMemberInOccupiedActionSpace() {
        return true;
    }

}
