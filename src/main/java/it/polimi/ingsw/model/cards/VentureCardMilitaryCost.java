package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.resource.Resource;

/**
 * This class is a structure basically that handles Military cost / requirement issue.
 */
public class VentureCardMilitaryCost {
    //you need to have:
    Resource resourceRequirement;
    //in order to spend:
    Resource resourceCost;

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
}
