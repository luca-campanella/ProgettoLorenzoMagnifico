package it.polimi.ingsw.model.resource;

/**
 * Created by higla on 16/05/2017.
 */
public class Resource{
    private ResourceType type;
    private int value;

    public Resource(ResourceType typeOfResource, int valueOfResource){
        this.type = typeOfResource;
        this.value = valueOfResource;
    }
    public String getResourceAbbreviation()
    {
        return type.getAbbreviation() + " " + value + " ";
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
