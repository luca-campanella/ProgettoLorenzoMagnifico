package it.polimi.ingsw.model.leaders.leadersabilities.PermanenteLeaderAbility;

/**
 * This ability lets you place a family member in an occupied tower without having to spend the additional 3 coins
 * This ability will usually be implemented by "Filippo Bunelleschi"
 */
public class NotToSpendForOccupiedTowerLeaderAbility extends AbstractPermanentLeaderAbility {

    public NotToSpendForOccupiedTowerLeaderAbility() {
        super();
    }

    /**
     * Override of the method to return the correct value of this particular ability
     * @return true
     */
    public boolean hasNotToSpendForOccupiedTower() {
        return true;
    }

    @Override
    public String getAbilityDescription() {
        return "You don’t have to spend 3 coins when you place your Family Members in a Tower that is already occupied.";
    }
}
