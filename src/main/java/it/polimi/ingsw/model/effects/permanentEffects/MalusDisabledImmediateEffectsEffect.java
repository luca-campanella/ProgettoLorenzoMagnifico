package it.polimi.ingsw.model.effects.permanentEffects;

/**
 * This effect disables all immediate actions space effect
 */
public class MalusDisabledImmediateEffectsEffect extends  AbstractPermanentEffect{

    /**
     * Over this level (floor) all permanent effects are disabled (in this level too)
     * It is not a dice value, but a floor / level value, because the dice value of a tower can be loaded from file
     */
    int firstLevelDisabled;

    /**
     * This method returns if the player has immediate effects disabled on a certain level of towers
     * @param towerLevel the level of the tower to check if at that level the effects are disabled
     * @return true if the level is higher than the limit level over which immediate effects are disabled
     */
    @Override
    public boolean isImmediateEffectDisabled(int towerLevel)
    {
        if(towerLevel >= firstLevelDisabled)
            return true;
        return false;
    }

    public String getShortDescription(){
        return "!ImmEffLv>" + firstLevelDisabled;
    }
    public String getDescription(){
        return "Disable all immediate effects on action spaces of towers higher than level" + firstLevelDisabled;
    }
}

