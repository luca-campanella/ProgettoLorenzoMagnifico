package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.effects.EffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;

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

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }

    public EffectInterface getEffect() {
        return effect;
    }

    public void setEffect(EffectInterface effect) {
        this.effect = effect;
    }
    public String getEffectShortDescription()
    {
        return effect.descriptionShortOfEffect();
    }
    public String getEffectDescription()
    {
        return effect.descriptionOfEffect();
    }
    public void getEffect(EffectInterface effect) {
        this.effect = effect;
    }

}
