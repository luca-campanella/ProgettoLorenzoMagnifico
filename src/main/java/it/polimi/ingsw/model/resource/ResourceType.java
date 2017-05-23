package it.polimi.ingsw.model.resource;

/**
 * Created by higla on 16/05/2017.
 */
public enum ResourceType {
    //L means lackey, Lacchè
    WOOD ("W"), STONE("S"), SERVANT("L"), COIN("C"), FAITH_POINT("F"), MILITARY_POINT("M"), VICTORY_POINT("V");
    private String abbreviation;
    private ResourceType(String abbreviation){
        this.abbreviation = abbreviation;
    }
    public String getAbbreviation()
    {
        return abbreviation;
    }
}
