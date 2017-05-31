package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingConditionedEffect;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class VentureCard extends AbstractCard{
    private ArrayList<TakeOrPaySomethingEffect> costChoiceResource;
    private ArrayList<TakeOrPaySomethingConditionedEffect> costChoiceMilitary;
    private int victoryEndPoints;

    //TODO: method finalVictoryPoints
    /*private Resource getFinalVictoryPoints{

    }*/
    public ArrayList<? extends ImmediateEffectInterface> getCost(){
        ArrayList<TakeOrPaySomethingEffect> costFusion = new ArrayList<>();
        if(costChoiceResource != null)
            costFusion.addAll(costChoiceResource);
        try {
            costFusion.addAll(costChoiceMilitary);
        }catch(NullPointerException e)
        {
            ;
        }
            return  costFusion;
    }
    public ArrayList<TakeOrPaySomethingEffect> getCostChoiceResource() {
        return costChoiceResource;
    }

    public void setCostChoiceResource(ArrayList<TakeOrPaySomethingEffect> costChoiceResource) {
        this.costChoiceResource = costChoiceResource;
    }

    public ArrayList<TakeOrPaySomethingConditionedEffect> getCostChoiceMilitary() {
        return costChoiceMilitary;
    }

    public void setCostChoiceMilitary(ArrayList<TakeOrPaySomethingConditionedEffect> costChoiceMilitary) {
        this.costChoiceMilitary = costChoiceMilitary;
    }

    public int getVictoryEndPoints() {
        return victoryEndPoints;
    }

    public void setVictoryEndPoints(int victoryEndPoints) {
        this.victoryEndPoints = victoryEndPoints;
    }
    public String secondEffect(){
        return "+V " + victoryEndPoints;
    }
}
