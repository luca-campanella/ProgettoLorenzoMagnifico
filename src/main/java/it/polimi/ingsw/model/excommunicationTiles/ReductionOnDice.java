package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

import java.util.ArrayList;

/**
 * This class models the excommunication tile that decreases some of yours family members diceValue by a certain value
 * you can still use your servants to increase value of your actions
 */
public class ReductionOnDice {
    //colors effected
    ArrayList<DiceAndFamilyMemberColor> colorsEffected;
    //amount of malus
    int malusValue;
}
