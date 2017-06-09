package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.effects.immediateEffects.GainOrPayResourceConditionedEffect;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;

import java.util.ArrayList;

/**
 * This class is fro purple cards.
 */
//todo: maybe victoryEndPoints are a resource and not a number -- Arto
public class VentureCard extends AbstractCard{
    private static CardColorEnum cardColor = CardColorEnum.PURPLE;
    //purple cards have 2 costs, one is on resources
    private ArrayList<GainResourceEffect> costChoiceResource;
    //the other on military points.
    private ArrayList<GainOrPayResourceConditionedEffect> costChoiceMilitary;

    private int victoryEndPoints;

    //TODO: method finalVictoryPoints
    /*private Resource getFinalVictoryPoints{

    }*/
    //this method i think could be useful to other classes diffrent from CLIPrinter
    /**
     * this method is generated to "merge" the 2 costs and allows the printer to print them all together.
     * @return a list with all costs
     */
    public ArrayList<? extends ImmediateEffectInterface> getCost(){
        ArrayList<GainResourceEffect> costFusion = new ArrayList<>();
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
    public ArrayList<GainResourceEffect> getCostChoiceResource() {
        return costChoiceResource;
    }

    public void setCostChoiceResource(ArrayList<GainResourceEffect> costChoiceResource) {
        this.costChoiceResource = costChoiceResource;
    }

    public ArrayList<GainOrPayResourceConditionedEffect> getCostChoiceMilitary() {
        return costChoiceMilitary;
    }

    public void setCostChoiceMilitary(ArrayList<GainOrPayResourceConditionedEffect> costChoiceMilitary) {
        this.costChoiceMilitary = costChoiceMilitary;
    }

    public int getVictoryEndPoints() {
        return victoryEndPoints;
    }

    public void setVictoryEndPoints(int victoryEndPoints) {
        this.victoryEndPoints = victoryEndPoints;
    }

    /**
     * venture cards hasn't really have  scond effect. They give victory points at the end f the game
     * @return a String with all points
     */
    public String secondEffect(){
        return "+V " + victoryEndPoints;
    }
    public CardColorEnum getColor(){
        return cardColor;
    }
}
