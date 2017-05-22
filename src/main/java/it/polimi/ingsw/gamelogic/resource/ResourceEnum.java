package it.polimi.ingsw.gamelogic.resource;

/**
 * Created by higla on 16/05/2017.
 */
public enum ResourceEnum {
    //L means lackey, Lacchè
    WOOD ("W"), STONE("S"), SERVANT("L"), COIN("C"), FAITH_POINT("F"), MILITARY_POINT("M"), VICTORY_POINT("V");
    private String abbreviation;
    private ResourceEnum(String abbreviation){
        this.abbreviation = abbreviation;
    }
    public String getAbbreviation()
    {
        return abbreviation;
    }
}
