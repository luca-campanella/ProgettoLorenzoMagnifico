package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class AbstractCard {
    private String name;
    private int period;
    ArrayList<ImmediateEffectInterface> immediateEffect;
    ArrayList<AbstractPermanentEffect> permanentEffect;



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

    public ArrayList<AbstractPermanentEffect> getPermanentEffect() {
        return permanentEffect;
    }

    public void setPermanentEffect(ArrayList<AbstractPermanentEffect> permanentEffect) {
        this.permanentEffect = permanentEffect;
    }
}
