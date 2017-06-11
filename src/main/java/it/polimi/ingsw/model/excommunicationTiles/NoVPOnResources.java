package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * You lose n victory points each m resource you have
 */
public class NoVPOnResources extends AbstractExcommunicationTileEffect{
    //number of victory points lost. (n) per resource
    //private int numberOfVictoryPointLostPerResource;
    //resources where the excommunication i1s applied (m)
    private ArrayList<Resource> resourcesEffected;

    /**
     * this method gives less victory points for a certain list of resources.
     * @param resources
     * @return the number of victory points to sub
     */
    public int noVPonResource(ArrayList<Resource> resources)
    {
        int countNumberOfResources = 0;
        int numberOfVPLost = 0;
        //for each resource that player has at the end of the game
        for(Resource playersResource : resources)
            //i take a resource where the excommunication has effect
            for(Resource effectedResourceIterator : resourcesEffected)
                //and i check if they are the same resource.
                if(playersResource.getType() == effectedResourceIterator.getType()) {
                    //if it's true, it means that that resource is effected by an excommunication
                    //so i just count the number of VP lost for that resource and i add them to numberOfVPLost
                    //which will be returned at the end of the cicles
                    numberOfVPLost += playersResource.getValue()/ effectedResourceIterator.getValue();
                }
        //numberOfVPLost = countNumberOfResources/numberOfVictoryPointLostPerResource;
        return numberOfVPLost;
    }

    public String getShortEffectDescription(){
        String temp = "For each: ";
        for(Resource i : resourcesEffected)
            temp += i.getType().toString() + " you have -" + i.getValue() + " VP ";
        return temp;
    }



    public void setResourcesEffected(ArrayList<Resource> resourcesEffected) {
        this.resourcesEffected = resourcesEffected;
    }

    public NoVPOnResources(ArrayList<Resource> resourcesEffected) {
        this.resourcesEffected = resourcesEffected;
    }
}
