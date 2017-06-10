package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class is fro purple cards.
 */
//todo: maybe victoryEndPoints are a resource and not a number -- Arto
public class VentureCard extends AbstractCard{
    private static CardColorEnum cardColor = CardColorEnum.PURPLE;
    //purple cards have 2 costs, one is on resources
    private ArrayList<Resource> costChoiceResource;
    //the other on military points.
    private VentureCardMilitaryCost costChoiceMilitary;

    private int victoryEndPoints;

    //TODO: method finalVictoryPoints
    /*private Resource getFinalVictoryPoints{

    }*/
    //this method i think could be useful to other classes diffrent from CLIPrinter
    /**
     * this method is generated to "merge" the 2 costs and allows the printer to print them all together.
     * @return a list with all costs
     */
    @Override
    public ArrayList<Resource> getCost(){
        ArrayList<Resource> costFusion = new ArrayList<>();
        if(costChoiceResource != null)
            costFusion.addAll(costChoiceResource);
        if(costChoiceMilitary != null)
            costFusion.add(costChoiceMilitary.getResourceCost());
        return  costFusion;
    }

    @Override
    public ArrayList<Resource> getCostAskChoice(ChoicesHandlerInterface choicesController) {
        return null;
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

    public void setCostChoiceMilitary(VentureCardMilitaryCost costChoiceMilitary) {
        this.costChoiceMilitary = costChoiceMilitary;
    }
}
