package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceCollector;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;
import java.util.List;

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
    //TODO
    @Override
    public ArrayList<Resource> getCost(){

        return costChoiceResource;

    }

    public VentureCardMilitaryCost getCostChoiceMilitary(){

        return costChoiceMilitary;
    }

    @Override
    public List<Resource> getCostAskChoice(ChoicesHandlerInterface choicesController) {
        if(costChoiceResource == null && costChoiceMilitary == null)
            return new ArrayList<Resource>(0);
        if(costChoiceMilitary == null)
            return costChoiceResource;
        if(costChoiceResource == null) {
            ArrayList<Resource> tmp = new ArrayList<Resource>(1);
            tmp.add(costChoiceMilitary.getResourceCost());
            return tmp;
        }
        return choicesController.callbackOnVentureCardCost(getName(), costChoiceResource, costChoiceMilitary);
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

    /**
     * this method is used to control if the player can buy the card
     * @param resource are the resources of the player
     * @return true if the player can buy the card, false otherwise
     */
    @Override
    public boolean canBuy(ResourceCollector resource) {
        if(resource.checkIfContainable(costChoiceResource))
            return true;
        if(resource.getResource(costChoiceMilitary.getResourceRequirement().getType())>costChoiceMilitary.getResourceRequirement().getValue())
            return true;
        return false;
    }

    public void setCostChoiceMilitary(VentureCardMilitaryCost costChoiceMilitary) {
        this.costChoiceMilitary = costChoiceMilitary;
    }
}
