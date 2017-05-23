package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;

/**
 * Created by higla on 16/05/2017.
 */
public abstract class AbstractActionSpace {
    private int diceValue;
    private ArrayList<ImmediateEffectInterface> effects;

    protected AbstractActionSpace() {
        effects = new ArrayList<>(1);
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

    public ArrayList<ImmediateEffectInterface> getEffects() {
        return effects;
    }

    public void addEffect(ImmediateEffectInterface effect) {
        effects.add(effect);
    }
    public String getEffectShortDescription()
    {
        String desc = new String();
        
        for(ImmediateEffectInterface i : effects)
            desc += i.descriptionShortOfEffect();
        
        return desc;
    }
    public String getEffectDescription()
    {
        String desc = new String();

        for(ImmediateEffectInterface i : effects)
            desc += i.descriptionOfEffect();

        return desc;
    }
    public ArrayList<ImmediateEffectInterface> getEffects(ImmediateEffectInterface effect) {
        return effects;
    }

}
