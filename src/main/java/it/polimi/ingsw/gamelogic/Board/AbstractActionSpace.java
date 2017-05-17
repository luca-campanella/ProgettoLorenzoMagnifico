package it.polimi.ingsw.gamelogic.Board;

import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public abstract class AbstractActionSpace {
    public int DICEVALUE;
    public EffectInterface EFFECT;

    protected AbstractActionSpace() {
    }

    /**
     * this method lets you perform an action
     */
    abstract public void performAction(FamilyMember familyMember);


    public int getDICEVALUE() {
        return DICEVALUE;
    }

    public void setDICEVALUE(int DICEVALUE) {
        this.DICEVALUE = DICEVALUE;
    }

    public EffectInterface getEFFECT() {
        return EFFECT;
    }

    public void setEFFECT(EffectInterface EFFECT) {
        this.EFFECT = EFFECT;
    }
}
