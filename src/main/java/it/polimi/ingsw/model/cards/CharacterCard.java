package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.PayResourceEffect;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;

import java.util.ArrayList;

/**
 * This is the class of all blue cards.
 */
public class CharacterCard extends AbstractCard {
    //blue cards have a cost (usually they cost coins)
    private ArrayList<PayResourceEffect> cost;
    //and a permanent effect, that buffs players actions
    ArrayList<AbstractPermanentEffect> permanentEffect;

    //TODO
    public boolean checkEffect()
    {
        return true;
    }

    public ArrayList<PayResourceEffect> getCost() {
        return cost;
    }

    public void setCost(ArrayList<PayResourceEffect> cost) {
        this.cost = cost;
    }

    public ArrayList<AbstractPermanentEffect> getPermanentEffects() {
        return permanentEffect;
    }

    public void setPermanentEffects(ArrayList<AbstractPermanentEffect> permanentEffect) {
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
