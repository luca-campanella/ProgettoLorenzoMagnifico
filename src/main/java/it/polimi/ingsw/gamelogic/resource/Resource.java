package it.polimi.ingsw.gamelogic.resource;

/**
 * Created by higla on 16/05/2017.
 */
public class Resource{
    private ResourceEnum type;
    private int value;

    public Resource(ResourceEnum typeOfResource, int valueOfResource){
        this.type = typeOfResource;
        this.value = valueOfResource;
    }
    public String getResourceAbbreviation()
    {
        return type.getAbbreviation() + " " + value + " ";
    }
}
