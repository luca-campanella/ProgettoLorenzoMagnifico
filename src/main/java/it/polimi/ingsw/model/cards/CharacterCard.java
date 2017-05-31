package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class CharacterCard extends AbstractCard {
    private ArrayList<TakeOrPaySomethingEffect> cost;
    ArrayList<AbstractPermanentEffect> permanentEffect;

    //TODO
    public boolean checkEffect()
    {
        return true;
    }

    public ArrayList<TakeOrPaySomethingEffect> getCost() {
        return cost;
    }

    public void setCost(ArrayList<TakeOrPaySomethingEffect> cost) {
        this.cost = cost;
    }

    public ArrayList<AbstractPermanentEffect> getPermanentEffect() {
        return permanentEffect;
    }

    public void setPermanentEffect(ArrayList<AbstractPermanentEffect> permanentEffect) {
        this.permanentEffect = permanentEffect;
    }
    public String secondEffect(){
        String temp = new String();
        for(int i=0; i<permanentEffect.size(); i++)
            temp += permanentEffect.get(i).getShortDescription();
        return temp;
    }
}
