package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class BuildingCard extends AbstractCard{

    /**
     * The array list of the cost to pay when taking the card
     */
    private ArrayList<TakeOrPaySomethingEffect> cost;

    /**
     * the array list of effects called when a Building action is perfomed.
     * This arraylist will usually be filled with {@link it.polimi.ingsw.model.effects.immediateEffects.PayForSomethingEffect}
     */
    private ArrayList<ImmediateEffectInterface> effectsOnBuilding;

    /**
     * this parameter indicates minimum dice's value to attivate card's build effect
     */
    private int buildEffectValue;

    //apply to player deve avere anche il valore del dado.
    private void applyEffectsToPlayer(Player player){
        ;
    }

    public ArrayList<TakeOrPaySomethingEffect> getCost() {
        return cost;
    }

    public void setCost(ArrayList<TakeOrPaySomethingEffect> cost) {
        this.cost = cost;
    }

    public int getBuildEffectValue() {
        return buildEffectValue;
    }

    public void setBuildEffectValue(int buildEffectValue) {
        this.buildEffectValue = buildEffectValue;
    }

    public ArrayList<ImmediateEffectInterface> getEffectsOnBuilding() {
        return effectsOnBuilding;
    }

    public void setEffectsOnBuilding(ArrayList<ImmediateEffectInterface> effectsOnBuilding) {
        this.effectsOnBuilding = effectsOnBuilding;
    }
    public String secondEffect(){
        String temp = new String();
        for(int i=0; i<effectsOnBuilding.size();i++)
            temp += effectsOnBuilding.get(i).descriptionShortOfEffect();
        return temp;
    }
}
