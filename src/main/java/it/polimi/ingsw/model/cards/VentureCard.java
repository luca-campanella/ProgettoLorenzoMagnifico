package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class VentureCard extends AbstractCard{
    private ArrayList<TakeOrPaySomethingEffect> costChoiceResource;
    private ArrayList<TakeOrPaySomethingEffect> costChoiceMilitary;
    private int victoryEndPoints;

    //TODO: method finalVictoryPoints
    /*private Resource getFinalVictoryPoints{

    }*/

    public ArrayList<TakeOrPaySomethingEffect> getCostChoiceResource() {
        return costChoiceResource;
    }

    public void setCostChoiceResource(ArrayList<TakeOrPaySomethingEffect> costChoiceResource) {
        this.costChoiceResource = costChoiceResource;
    }

    public ArrayList<TakeOrPaySomethingEffect> getCostChoiceMilitary() {
        return costChoiceMilitary;
    }

    public void setCostChoiceMilitary(ArrayList<TakeOrPaySomethingEffect> costChoiceMilitary) {
        this.costChoiceMilitary = costChoiceMilitary;
    }

    public int getVictoryEndPoints() {
        return victoryEndPoints;
    }

    public void setVictoryEndPoints(int victoryEndPoints) {
        this.victoryEndPoints = victoryEndPoints;
    }
}
