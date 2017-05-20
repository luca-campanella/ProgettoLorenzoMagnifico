package it.polimi.ingsw.gamelogic.Resource;

/**
 * Created by higla on 16/05/2017.
 */
public enum ResourceEnum {
    WOOD ("W"), STONE("S"), SERVANT("S"), COIN("C"), FAITH_POINTS("F"), MILITARY_POINTS("M"), VICTORY_POINTS("V");
    private String abbreviation;
    private ResourceEnum(String abbreviation){
        this.abbreviation = abbreviation;
    }
    public String getAbbreviation()
    {
        return abbreviation;
    }
}
