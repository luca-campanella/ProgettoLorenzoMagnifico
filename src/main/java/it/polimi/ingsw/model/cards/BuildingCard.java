package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class BuildingCard extends AbstractCard{
    private ArrayList<TakeOrPaySomethingEffect> cost;
    private ArrayList<ImmediateEffectInterface> effectsOnBuilding;

    /*
    this parameter indicates minimum dice's value to attivate card's build effect
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
}
