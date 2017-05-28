package it.polimi.ingsw.model.leaders.leadersabilities;

/**
 * This ability lets you place a family member in an occupied tower without having to spend the additional 3 coins
 * This ability will usually be implemented by "Filippo Bunelleschi"
 */
public class NotToSpendForOccupiedTowerLeaderAbility extends AbstractLeaderAbility {

    public NotToSpendForOccupiedTowerLeaderAbility() {
        super();
    }

    /**
     * This method will usually be overridden by "Filippo Bunelleschi" ability
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
