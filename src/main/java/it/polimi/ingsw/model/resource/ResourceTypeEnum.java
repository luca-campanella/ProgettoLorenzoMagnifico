package it.polimi.ingsw.model.resource;

/**
 * Enum for types of resources
 */
public enum ResourceTypeEnum {

    WOOD("W", "Wood"),
    STONE("S", "Stone"),
    SERVANT("L", "Servant"), //L means lackey, Lacchè
    COIN("C", "Coin"),
    FAITH_POINT("F", "Faith point"),
    MILITARY_POINT("M", "Military point"),
    VICTORY_POINT("V", "Victory point");
    private final String fullName;
    private final String abbreviation;

    private ResourceTypeEnum(String abbreviation, String fullName){
        this.abbreviation = abbreviation;
        this.fullName = fullName;
    }
    public String getAbbreviation()
    {
        return abbreviation;
    }

    public String getFullName() {
        return fullName;
    }
}
