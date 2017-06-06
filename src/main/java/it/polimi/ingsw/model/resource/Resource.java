package it.polimi.ingsw.model.resource;

import java.io.Serializable;

/**
 * This class describe a particular resource and contains the resource type and its value
 */
public class Resource implements Serializable{
    /**
     * the type of the resource
     */
    private ResourceTypeEnum type;

    /**
     * the amount
     */
    private int value;

    public Resource(ResourceTypeEnum typeOfResource, int valueOfResource){
        this.type = typeOfResource;
        this.value = valueOfResource;
    }

    public String getResourceShortDescript()
    {
        return type.getAbbreviation() + " " + value + " ";
    }

    public String getResourceFullDescript() {
        if(value > 1 || value < 1)
             return value + " " + type.getFullName() + "s";
        return value + " " + type.getFullName();
    }

    public ResourceTypeEnum getType() {
        return type;
    }

    public void setType(ResourceTypeEnum type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
