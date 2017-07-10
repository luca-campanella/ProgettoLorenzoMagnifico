package it.polimi.ingsw.model.resource;

import it.polimi.ingsw.client.controller.ClientMainController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * This class contains an hashmap of all possible resources types,
 * it is used by {@link it.polimi.ingsw.model.player.Player} to keep track of his resources, but also in other contexts
 * This class also implements all the methods necessary to operate on the hashmap
 */
public class ResourceCollector implements Serializable{

    private EnumMap<ResourceTypeEnum, Integer> resourcesMap;

    /**
     * creates an empty object with all resources set to 0
     */
    public ResourceCollector() {
        resourcesMap = new EnumMap<ResourceTypeEnum, Integer>(ResourceTypeEnum.class);
        loadResource();
    }

    /**
     * creates an object filled with the resources contained in the arraylist
     * @param resources the resources to inizialize the object to
     */
    public ResourceCollector(List<Resource> resources) {
        this();
        addResource(resources);
    }

    /**
     * creates a copy of an existing {@link ResourceCollector} object
     * @param toBeCopied the {@link ResourceCollector} to be copied
     */
    public ResourceCollector(ResourceCollector toBeCopied) {
        this();
        addResource(toBeCopied);
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
     * this method is used to add an array of resources
     * @param resources the list of the resources, it contains the value and the type
     */
    public void addResource(List<Resource> resources){
        for(Resource resource : resources){
            addResource(resource);
        }
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
     * this method is used to sub an array of resources
     * @param resources the list of the resources, it contains the value and the type
     */
    public void subResource(List<Resource> resources){
        for(Resource resource : resources){
            subResource(resource);
        }
    }

    /**
     * this method is used to sub an multiple resources
     * @param resources the list of the resources
     */
    public void subResource(ResourceCollector resources){
        for(ResourceTypeEnum typeIter : ResourceTypeEnum.values())
            resourcesMap.put(typeIter, resourcesMap.get(typeIter) - resources.getResource(typeIter));
    }

    /**
     * this method is used to subtract a single resource,
     * Differently form {@link ResourceCollector#subResource(Resource)} the result can never be lower than zero
     * {@link Resource#getValue()} should be positive to work as a subtractor
     * @param resource the object of the resource, it contains the value and the type
     */
    public void subResourceSafely(Resource resource){
        int res = this.resourcesMap.get(resource.getType()) - resource.getValue();
        if(res < 0)
            res = 0;

        this.resourcesMap.put(resource.getType(),res);
    }

    /**
     * this method is used to sub an array of resources
     * Differently form {@link ResourceCollector#subResource(List)} the result can never be lower than zero
     * @param resources the list of the resources, it contains the value and the type
     */
    public void subResourcesSafely(List<Resource> resources){
        for(Resource resource : resources){
            subResourceSafely(resource);
        }
    }

    /**
     * Resets the value corresponding to the resource to the passed one, it doesn't sum, resets
     * @param res the resource type and the value to reset to
     */
    public void setResource(Resource res) {
        resourcesMap.put(res.getType(), res.getValue());
    }


    public int getResource(ResourceTypeEnum type){

        return resourcesMap.get(type);

    }

    public ArrayList<Resource> getAllResources(){

        ArrayList<Resource> ris = new ArrayList<>(7);

        for(ResourceTypeEnum resTypeIter : ResourceTypeEnum.values())
            ris.add(new Resource(resTypeIter, resourcesMap.get(resTypeIter)));

        return ris;

    }

    /**
     * This method checks if an arraylist of resources can be contained inside the saved resources,
     * for example it can check if the player has sufficient resources to play for a certain effect,
     * mostly used for {@link it.polimi.ingsw.model.effects.immediateEffects.PayForSomethingEffect} checks inside {@link ClientMainController}
     * @param resToCheck the arraylist of the resources the method checks if are contained or not
     * @return if the resources are contained or not
     */
    public boolean checkIfContainable(List<Resource> resToCheck) {
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
    public void addResource(ResourceCollector toBeAdded) {
        for(ResourceTypeEnum typeIter : ResourceTypeEnum.values())
            resourcesMap.put(typeIter, resourcesMap.get(typeIter) + toBeAdded.getResource(typeIter));
    }

    /**
     * this method is used to turn down to zero a type ofresource
     * @param typeEnum the type of resource to take down
     */
    public void resetResource(ResourceTypeEnum typeEnum) {
        resourcesMap.put(typeEnum, 0);
    }
}
