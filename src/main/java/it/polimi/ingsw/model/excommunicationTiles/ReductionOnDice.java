package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.board.CardColorEnum;

import java.util.ArrayList;

/**
 * This class models the excommunication tile that decreases some of yours family members diceValue by a certain value
 * you can still use your servants to increase value of your actions
 */
public class ReductionOnDice extends AbstractExcommunicationTileEffect{
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

}
