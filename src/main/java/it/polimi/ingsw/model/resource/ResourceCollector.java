package it.polimi.ingsw.model.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains an hashmap of all possible resources types,
 * it is used by {@link it.polimi.ingsw.model.player.Player} to keep track of his resources, but also in other contexts
 * This class also implements all the methods necessary to operate on the hashmap
 */
public class ResourceCollector implements Serializable{

    private HashMap<ResourceTypeEnum, Integer> resourcesMap;

    /**
     * creates an empty object with all resources set to 0
     */
    public ResourceCollector() {
        resourcesMap = new HashMap<>(7);
        loadResource();
    }

    /**
     * creates an object filled with the resources contained in the arraylist
     * @param resources the resources to inizialize the object to
     */
    public ResourceCollector(ArrayList<Resource> resources) {
        this();
        addResources(resources);
    }

    /**
     * creates a copy of an existing {@link ResourceCollector} object
     * @param toBeCopied the {@link ResourceCollector} to be copied
     */
    public ResourceCollector(ResourceCollector toBeCopied) {
        this();
        addResources(toBeCopied);
    }

    /**
     * you load all the resources needed, initializing the hashmap
     */
    private void loadResource(){
        resourcesMap.put(ResourceTypeEnum.COIN, 0);
        resourcesMap.put(ResourceTypeEnum.WOOD, 0);
        resourcesMap.put(ResourceTypeEnum.STONE, 0);
        resourcesMap.put(ResourceTypeEnum.SERVANT, 0);
        resourcesMap.put(ResourceTypeEnum.FAITH_POINT, 0);
        resourcesMap.put(ResourceTypeEnum.MILITARY_POINT, 0);
        resourcesMap.put(ResourceTypeEnum.VICTORY_POINT, 0);
    }

    /**
     * this method is used to add a single resource, if {@link Resource#getValue()} < 0 subtracts the resource instead
     * @param resource the object of the resource, it contains the value and the type
     */
    public void addResource(Resource resource){

        this.resourcesMap.put(resource.getType(),this.resourcesMap.get(resource.getType())+resource.getValue());

    }

    /**
     * this method is used to subtract a single resource,
     * {@link Resource#getValue()} should be positive to work as a subtractor
     * @param resource the object of the resource, it contains the value and the type
     */
    public void subResource(Resource resource){

        this.resourcesMap.put(resource.getType(),this.resourcesMap.get(resource.getType())-resource.getValue());

    }

    /**
     * Resets the value corresponding to the resource to the passed one, it doesn't sum, resets
     * @param res the resource type and the value to reset to
     */
    public void setResource(Resource res) {
        resourcesMap.put(res.getType(), res.getValue());
    }

    /**
     * this method is used to add an array of resources
     * @param resources the arraylist of the resources, it contains the value and the type
     */
    public void addResources(ArrayList<Resource> resources){

        for(Resource resource : resources){
            this.resourcesMap.put(resource.getType(),this.resourcesMap.get(resource.getType())+resource.getValue());
        }

    }

    public int getResource(ResourceTypeEnum type){

        return resourcesMap.get(type);

    }

    /**
     * This method checks if an arraylist of resources can be contained inside the saved resources,
     * for example it can check if the plahyer as sufficient resources to play for a certain effect,
     * mostly used for {@link it.polimi.ingsw.model.effects.immediateEffects.PayForSomethingEffect} checks inside {@link it.polimi.ingsw.client.controller.ClientMain}
     * @param resToCheck the arraylist of the resources the method checks if are contained or not
     * @return if the resources are contained or not
     */
    public boolean checkIfContainable(ArrayList<Resource> resToCheck) {
        for(Resource resIter : resToCheck) {
            if(resourcesMap.get(resIter.getType()) < resIter.getValue())
                return false;
        }

        return true;
    }

    /**
     * sum the {@link ResourceCollector} to this
     * @param toBeAdded the {@link ResourceCollector} to be added to this
     */
    public void addResources(ResourceCollector toBeAdded) {
        resourcesMap.put(ResourceTypeEnum.COIN, resourcesMap.get(ResourceTypeEnum.COIN) + toBeAdded.getResource(ResourceTypeEnum.COIN));
        resourcesMap.put(ResourceTypeEnum.WOOD, resourcesMap.get(ResourceTypeEnum.WOOD) + toBeAdded.getResource(ResourceTypeEnum.WOOD));
        resourcesMap.put(ResourceTypeEnum.STONE, resourcesMap.get(ResourceTypeEnum.STONE) + toBeAdded.getResource(ResourceTypeEnum.STONE));
        resourcesMap.put(ResourceTypeEnum.SERVANT, resourcesMap.get(ResourceTypeEnum.SERVANT) + toBeAdded.getResource(ResourceTypeEnum.SERVANT));
        resourcesMap.put(ResourceTypeEnum.FAITH_POINT, resourcesMap.get(ResourceTypeEnum.FAITH_POINT) + toBeAdded.getResource(ResourceTypeEnum.FAITH_POINT));
        resourcesMap.put(ResourceTypeEnum.MILITARY_POINT, resourcesMap.get(ResourceTypeEnum.MILITARY_POINT) + toBeAdded.getResource(ResourceTypeEnum.MILITARY_POINT));
        resourcesMap.put(ResourceTypeEnum.VICTORY_POINT, resourcesMap.get(ResourceTypeEnum.VICTORY_POINT) + toBeAdded.getResource(ResourceTypeEnum.VICTORY_POINT));
    }
}
