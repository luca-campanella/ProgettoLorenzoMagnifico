package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class models the excommunication tile that decreases some of yours family members diceValue by a certain value
 * you can still use your servants to increase value of your actions
 */
public class ReductionOnDiceEffect extends AbstractExcommunicationTileEffect{
    //colors effected
    ArrayList<DiceAndFamilyMemberColorEnum> colorsEffected;
    //amount of malus
    private int malusValue;

    /**
     * this method check if the color of the dice you're using is effected by an excommunicationEffect
     * @param colorOfDice your diceValue is decreased by malusValue
     * @return
     */
    public int reductionOnDice(DiceAndFamilyMemberColorEnum colorOfDice)
    {
        for(DiceAndFamilyMemberColorEnum i : colorsEffected)
            if(i == colorOfDice)
                return malusValue;
        return 0;
    }
    public String getShortEffectDescription(){
        String temp = "-"+ malusValue + " OnDice colors: ";
        for(DiceAndFamilyMemberColorEnum i : colorsEffected)
            temp += i.toString() + " ";
        return temp;
    }

    public ReductionOnDiceEffect(ArrayList<DiceAndFamilyMemberColorEnum> colorsEffected, int malusValue) {
        this.colorsEffected = colorsEffected;
        this.malusValue = malusValue;
    }

    public void setColorsEffected(ArrayList<DiceAndFamilyMemberColorEnum> colorsEffected) {
        this.colorsEffected = colorsEffected;
    }

    public void setMalusValue(int malusValue) {
        this.malusValue = malusValue;
    }
}
