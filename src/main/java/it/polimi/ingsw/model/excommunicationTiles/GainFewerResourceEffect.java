package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.resource.Resource;

/**
 * this class models the first period excommunications tile that gives you fewer resources each time you take some resources.
 */
public class GainFewerResourceEffect extends AbstractExcommunicationTileEffect{
    //type of resource effected by the excommunication and amount of excommunication
    private Resource resourceExcommunication;

    /**
     * this method returns the value of the excommunication. Controller will then
     * @param resource
     * @return
     */
    int gainFewResource(Resource resource){
        if(resource.getType().equals(resourceExcommunication.getType()))
            //resource.setValue(resource.getValue()- resourceExcommunication.getValue());
        return resourceExcommunication.getValue();
        //in case resources type doesn't match, it returns 0
        return 0;
    }

    public GainFewerResourceEffect(Resource resourceExcommunication) {
        this.resourceExcommunication = resourceExcommunication;
    }

    public String getShortEffectDescription(){
        return "-"+ resourceExcommunication.getResourceShortDescript() + "OnResource";
    }
}
