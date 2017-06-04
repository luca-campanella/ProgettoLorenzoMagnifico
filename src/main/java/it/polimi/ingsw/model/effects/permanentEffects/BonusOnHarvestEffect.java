package it.polimi.ingsw.model.effects.permanentEffects;

/**
 * This class handles the bonus on the dice when the player harvests
 */
public class BonusOnHarvestEffect extends AbstractPermanentEffect {
    private int bonus;

    public BonusOnHarvestEffect(int bonus) {
        this.bonus = bonus;
    }

    /**
     * This method returns the bonus on the dice when the player performs an harvest action
     * @return the bonus
     */
    @Override
    public int getBonusOnHarvest() {
        return bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getShortDescription() {
        return "+" + bonus + " OnH";
    }

    public String getDescription() {
        return "gives a player " + bonus + " on the dice, when he harvests";
    }
}
