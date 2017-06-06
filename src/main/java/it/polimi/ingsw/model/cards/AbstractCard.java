package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract card class.
 */
public abstract class AbstractCard implements Serializable{
    private String name;
    private int period;
    ArrayList<ImmediateEffectInterface> immediateEffect;

    public abstract ArrayList<? extends ImmediateEffectInterface> getCost();
    public abstract String secondEffect();
    //abstract public int characteristicValue();
    public void addImmediateEffect(ImmediateEffectInterface effect){
        immediateEffect.add(effect);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public ArrayList<ImmediateEffectInterface> getImmediateEffect() {
        return immediateEffect;
    }

    public void setImmediateEffect(ArrayList<ImmediateEffectInterface> immediateEffect) {
        this.immediateEffect = immediateEffect;
    }


}
