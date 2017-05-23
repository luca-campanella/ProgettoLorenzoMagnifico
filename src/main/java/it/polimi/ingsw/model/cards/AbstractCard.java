package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.EffectInterface;
import it.polimi.ingsw.model.effects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.PermanentEffectInterface;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class AbstractCard {
    private String name;
    private int period;
    ArrayList<ImmediateEffectInterface> immediateEffect;
    ArrayList<PermanentEffectInterface> permanentEffect;



    public void addImmediateEffect(ImmediateEffectInterface effect){
        immediateEffect.add(effect);
    }

}
