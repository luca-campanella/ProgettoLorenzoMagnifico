package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

/**
 * this class models the first period excommunications tile that gives you fewer resources each time you take some resources.
 */
public class GainFewerResourceEffect extends AbstractExcommunicationTileEffect{
    //type of resource effected by the excommunication and amount of excommunication
    private Resource resourceExcommunication;

    /**
     * this method returns the value of the excommunication. Controller will then
     *
     * @param resourceType@return
     */
    @Override
    public int gainFewResource(ResourceTypeEnum resourceType){
        if(resourceType == resourceExcommunication.getType())
            return resourceExcommunication.getValue();
        //in case resources type doesn't match, it returns 0
        return 0;
    }
    //todo: remove this line resource.setValue(resource.getValue()- resourceExcommunication.getValue());

    public GainFewerResourceEffect(Resource resourceExcommunication) {
        this.resourceExcommunication = resourceExcommunication;
    }

    public String getShortEffectDescription(){
        return "-"+ resourceExcommunication.getResourceShortDescript() + "OnResource";
    }
}
