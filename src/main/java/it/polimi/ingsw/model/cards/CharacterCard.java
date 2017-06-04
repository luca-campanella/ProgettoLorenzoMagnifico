package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.GainSomethingEffect;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;

import java.util.ArrayList;

/**
 * This is the class of all blue cards.
 */
public class CharacterCard extends AbstractCard {
    //blue cards have a cost (usually they cost coins)
    private ArrayList<GainSomethingEffect> cost;
    //and a permanent effect, that buffs players actions
    ArrayList<AbstractPermanentEffect> permanentEffect;

    //TODO
    public boolean checkEffect()
    {
        return true;
    }

    public ArrayList<GainSomethingEffect> getCost() {
        return cost;
    }

    public void setCost(ArrayList<GainSomethingEffect> cost) {
        this.cost = cost;
    }

    public ArrayList<AbstractPermanentEffect> getPermanentEffect() {
        return permanentEffect;
    }

    public void setPermanentEffect(ArrayList<AbstractPermanentEffect> permanentEffect) {
        this.permanentEffect = permanentEffect;
    }

    /**
     * this method prints all effects available in this class
     * @return
     */
    public String secondEffect(){
        String temp = new String();
        for(int i=0; i<permanentEffect.size(); i++)
            temp += permanentEffect.get(i).getShortDescription();
        return temp;
    }
}
