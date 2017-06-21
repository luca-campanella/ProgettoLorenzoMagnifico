package it.polimi.ingsw.model.player;

import java.util.HashMap;
import java.util.Map;

/**
 * the colors of the family member and ogf the dices
 */
public enum DiceAndFamilyMemberColorEnum {

    WHITE(0),
    BLACK(1),
    ORANGE(2),
    NEUTRAL(3);

    private final int value;
    private static Map<Integer, DiceAndFamilyMemberColorEnum> map;

    static {
        map = new HashMap<Integer, DiceAndFamilyMemberColorEnum>();
        for (DiceAndFamilyMemberColorEnum enumIter : DiceAndFamilyMemberColorEnum.values()) {
            map.put(enumIter.getIntegerValue(), enumIter);
        }
    }

    DiceAndFamilyMemberColorEnum(int value) {
        this.value = value;
    }

    public int getIntegerValue() {
        return value;
    }

    public static DiceAndFamilyMemberColorEnum valueOf(int enumVal) {
        return map.get(enumVal);
    }
}


