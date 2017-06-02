package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

import java.util.Random;

/**
 * this class has the value of different dices
 */
public class Dice {

    /**
     * color of the dice (and of the family member)
     */
    private DiceAndFamilyMemberColor color;

    /**
     * random is an attribute to use random method
     */
    private Random random;

    /**
     * value of the dice
     */
    private int value;
    //this is the constructor
    public Dice(DiceAndFamilyMemberColor color){
        this.color=color;
        random= new Random();
    }

    /**
     * throw the dice to obtain a new value (1-6)
     * @return the value of the dice
     */
    public void throwDice() {

        if(color == DiceAndFamilyMemberColor.NEUTRAL)
            value=0;
        else
            value = random.nextInt(5)+1;

    }

    public DiceAndFamilyMemberColor getColor(){
        return color;
    }

    public int getValue(){
        return  value;
    }
}
