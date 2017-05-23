package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.resource.ResourceType;

import java.util.HashMap;

/**
 * The main player class, no network
 */
public abstract class Player {

    private String nickname;

    //private PersonalBoard board;

    private HashMap<ResourceType, Integer> resource;

    public Player()
    {
        super();
        resource = new HashMap<>();
        loadResource();
    }

    public Player(String nickname)
    {
        this.nickname = nickname;
        loadResource();
    }

    /**
     * you load all the resources needed by the player
     */
    private void loadResource(){
        resource.put(ResourceType.COIN, 0);
        resource.put(ResourceType.WOOD, 0);
        resource.put(ResourceType.STONE, 0);
        resource.put(ResourceType.SERVANT, 0);
        resource.put(ResourceType.FAITH_POINT, 0);
        resource.put(ResourceType.MILITARY_POINT, 0);
        resource.put(ResourceType.VICTORY_POINT, 0);
    }

    public void addResource(ResourceType type, int value){

        Integer valueNow = resource.get(type);
        valueNow = valueNow + value;
    }

    public int getResource(ResourceType type){

        return resource.get(type);

    }

    public String getNickname()
    {
        return nickname;
    }

    protected void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
