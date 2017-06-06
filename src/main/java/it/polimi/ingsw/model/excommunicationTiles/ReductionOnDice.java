package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

import java.util.ArrayList;

/**
 * This class models the excommunication tile that decreases some of yours family members diceValue by a certain value
 * you can still use your servants to increase value of your actions
 */
public class ReductionOnDice extends AbstractExcommunicationTileEffect{
    //colors effected
    private ArrayList<DiceAndFamilyMemberColor> colorsEffected;
    //amount of malus
    private int malusValue;

    /**
     * this method check if the color of the dice you're using is effected by an excommunicationEffect
     * @param colorOfDice your diceValue is decreased by malusValue
     * @return
     */
    public int reductionOnDice(DiceAndFamilyMemberColor colorOfDice)
    {
        for(DiceAndFamilyMemberColor i : colorsEffected)
            if(i == colorOfDice)
                return malusValue;
        return 0;
    }

}
