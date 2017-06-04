package it.polimi.ingsw.model.effects.permanentEffects;

/**
 * This class handles the bonus on the dice when the player builds
 */
public class BonusOnBuildEffect extends AbstractPermanentEffect{
    private int bonus;
    public BonusOnBuildEffect(int bonus){
        this.bonus = bonus;
    }

    /**
     * This method returns the bonus on the dice when the player performs a build action
     * @return the bonus
     */
    @Override
    public int getBonusOnBuild()
    {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public String getShortDescription(){
        return "+"+bonus+" OnB";
    }
    public String getDescription(){
        return "gives a player " + bonus + " on the dice, when he builds";
    }
}
