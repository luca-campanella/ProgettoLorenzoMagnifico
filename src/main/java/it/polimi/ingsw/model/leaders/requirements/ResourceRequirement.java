package it.polimi.ingsw.model.leaders.requirements;

import it.polimi.ingsw.model.leaders.requirements.AbstractRequirement;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This is a requirement for a leader, the required type is a resource
 */
public class ResourceRequirement extends AbstractRequirement {
    Resource resReq = null;

    public ResourceRequirement(Resource resReq) {
        super();
        this.resReq = resReq;
    }

    public Resource getResReq() {
        return resReq;
    }
}
