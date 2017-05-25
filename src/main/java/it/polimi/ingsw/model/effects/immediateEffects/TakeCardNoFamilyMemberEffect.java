package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class TakeCardNoFamilyMemberEffect extends AbstractPerformActionEffect {
    private int diceValueOnTower;
    private ArrayList<CardColorEnum> towerColorsAllowed;
    @Override
    public void applyToPlayer(Player player) {
        ;
    }

    @Override
    public String descriptionOfEffect() {
        int i;
        String tmp = new String();
        for(i=0; i<towerColorsAllowed.size(); i++)
            tmp += " " + towerColorsAllowed.get(i).toString();
        return "This method allows player to place one dice on " + tmp;
    }
    @Override
    public String descriptionShortOfEffect() {
        return "Dice";
    }

    public int getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }
}
