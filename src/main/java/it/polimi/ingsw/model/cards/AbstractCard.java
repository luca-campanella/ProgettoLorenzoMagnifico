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

}
