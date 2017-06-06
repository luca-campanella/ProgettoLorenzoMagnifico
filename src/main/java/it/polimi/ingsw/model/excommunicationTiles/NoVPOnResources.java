package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * You lose n victory points each m resource you have
 */
public class NoVPOnResources extends AbstractExcommunicationTileEffect{
    //number of victory points lost. (n) per resource
    private int numberOfVictoryPointLostPerResource;
    //resources where the excommunication i1s applied (m)
    private ArrayList<Resource> resourcesEffected;

    /**
     * this method gives less victory points for a certain list of resources.
     * @param resource
     * @return the number of victory points to sub
     */
    public int noVPonResource(ArrayList<Resource> resource)
    {
        int countNumberOfResources = 0;
        int numberOfVPLost = 0;
        for(Resource iterator : resource)
            countNumberOfResources += iterator.getValue();
        numberOfVPLost = countNumberOfResources/numberOfVictoryPointLostPerResource;
        return numberOfVPLost;
    }

    public String getShortEffectDescription(){
        String temp = "-"+ numberOfVictoryPointLostPerResource + " VP for ";
        for(Resource i : resourcesEffected)
            temp += i.getResourceShortDescript() + " ";
        return temp;
    }

}
