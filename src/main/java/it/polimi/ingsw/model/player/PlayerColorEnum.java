package it.polimi.ingsw.model.player;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum represents the possible colors for the players
 */
public enum PlayerColorEnum {
    BLUE("blue", 0),
    GREEN("green", 1),
    YELLOW("yellow", 2),
    RED("red", 3);

    private final String colorName;
    private final int value;

    private static Map<Integer, PlayerColorEnum> map;

    static {
        map = new HashMap<Integer, PlayerColorEnum>();
        for (PlayerColorEnum enumIter : PlayerColorEnum.values()) {
            map.put(enumIter.getIntegerValue(), enumIter);
        }
    }

    PlayerColorEnum(String colorName, int value) {
        this.colorName = colorName;
        this.value = value;
    }

    public String getStringValue() {
        return colorName;
    }

    public int getIntegerValue() {
        return value;
    }

    public static PlayerColorEnum valueOf(int enumVal) {
        return map.get(enumVal);
    }
}
