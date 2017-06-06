package it.polimi.ingsw.model.effects.permanentEffects;

/**
 * This effect disables all immediate actions space effect
 */
public class MalusDisabledImmediateEffectsEffect extends  AbstractPermanentEffect{

    /**
     * Over this dice value (floor) all permanent effects are disabled (in this level too)
     */
    int minDiceValueDisabled;

    /**
     * This method returns if the player has immediate effects disabled on a certain level of towers, that has a certain dice requirement
     * @param requiredDiceValue the required dice value check if at that level the effects are disabled
     * @return true if the dice value is equal or higher than the limit over which immediate effects are disabled
     */
    @Override
    public boolean isImmediateEffectDisabled(int requiredDiceValue)
    {
        if(requiredDiceValue >= minDiceValueDisabled)
            return true;
        return false;
    }

    public String getShortDescription(){
        return "!ImmEffLv>=" + minDiceValueDisabled;
    }
    public String getDescription(){
        return "Disable all immediate effects on action spaces with a dice requirements equal or higher than" + minDiceValueDisabled;
    }
}

