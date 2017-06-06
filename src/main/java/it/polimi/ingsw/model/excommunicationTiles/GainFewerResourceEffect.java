package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.resource.Resource;

/**
 * this class models the first period excommunications tile that gives you fewer resources each time you take some resources.
 */
public class GainFewerResourceEffect extends AbstractExcommunicationTileEffect{
    //type of resource effected by the excommunication and amount of excommunication
    private Resource resourceExcommunication;

    public void setResourceExcommunication(Resource resourceExcommunication) {
        this.resourceExcommunication = resourceExcommunication;
    }

    public String getShortEffectDescription(){
        return "-"+ resourceExcommunication.getResourceShortDescript() + "OnResource";
    }
}
