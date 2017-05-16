package it.polimi.ingsw.gamelogic.Resource;

/**
 * Created by higla on 16/05/2017.
 */
public class Resource{
    private ResourceEnum type;
    private int value;

    Resource(ResourceEnum typeOfResource, int valueOfResource){
        this.type = typeOfResource;
        this.value = valueOfResource;
    }
}
