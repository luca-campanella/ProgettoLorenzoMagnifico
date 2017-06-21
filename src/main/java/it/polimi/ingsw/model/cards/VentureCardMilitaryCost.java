package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.resource.Resource;

import java.io.Serializable;

/**
 * This class is a structure basically that handles Military cost / requirement issue.
 */
public class VentureCardMilitaryCost implements Serializable {
    //you need to have:
    private Resource resourceRequirement;
    //in order to spend:
    private Resource resourceCost;

    public VentureCardMilitaryCost(Resource resourceRequirement, Resource resourceCost) {
        this.resourceRequirement = resourceRequirement;
        this.resourceCost = resourceCost;
    }

    public Resource getResourceRequirement() {
        return resourceRequirement;
    }

    public Resource getResourceCost() {
        return resourceCost;
    }

    public String getDescription(){
        return ("IF " + resourceRequirement.getResourceShortDescript() + "-" + resourceCost.getResourceShortDescript());
    }
}
