package it.polimi.ingsw.model.leaders.requirements;

import it.polimi.ingsw.model.player.Player;
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

    @Override
    public String getDescription() {
        return resReq.getResourceFullDescript();
    }

    /**
     * This method return true if the player meets the requirement
     * @param player the player to perform the check on
     * @return true if the requirement is met, false otherwise
     */
    @Override
    public boolean isMet(Player player) {
        if(player.getResource(resReq.getType()) >= resReq.getValue())
            return true;

        return false;
    }
}
