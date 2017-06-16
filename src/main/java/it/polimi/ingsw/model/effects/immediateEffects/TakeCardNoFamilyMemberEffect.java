package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * This method allows player to place a dice on a tower and take a card.
 */
public class TakeCardNoFamilyMemberEffect extends AbstractPerformActionEffect {
    private int diceValueOnTower;
    private ArrayList<CardColorEnum> towerColorsAllowed;
    //private ArrayList<...>
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName) {
       // todo: choicesHandlerInterface.callbackOnTakeCard(diceValueOnTower, towerColorsAllowed, SCONTO? )
    }

    @Override
    public String descriptionOfEffect() {
        int i;
        StringBuilder tmp = new StringBuilder();
        tmp.append("This method allows player to place one dice on ")
        for(i=0; i<towerColorsAllowed.size(); i++)
            tmp.append(" " + towerColorsAllowed.get(i).toString());
        return tmp.toString();
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
