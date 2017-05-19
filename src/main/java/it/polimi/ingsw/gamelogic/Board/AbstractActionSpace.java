package it.polimi.ingsw.gamelogic.Board;

import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public abstract class AbstractActionSpace {
    public int diceValue;
    public EffectInterface effect;

    protected AbstractActionSpace() {
    }

    /**
     * this method lets you perform an action
     */
    abstract public void performAction(FamilyMember familyMember);


    public int getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(int dicevalue) {
        this.diceValue = dicevalue;
    }

    public EffectInterface getEffect() {
        return effect;
    }

    public void setEFFECT(EffectInterface effect) {
        this.effect = effect;
    }
}
