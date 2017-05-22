package it.polimi.ingsw.gamelogic;

import it.polimi.ingsw.gamelogic.player.DiceAndFamilyMemberColor;

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

    public Dice(DiceAndFamilyMemberColor color){
        this.color=color;
        random= new Random();
    }

    /**
     * throw the dice to obtain a new value (1-6)
     * @return the value of the dice
     */
    public int throwDice() {
        value = random.nextInt(5)+1;
        return value;
    }

    public DiceAndFamilyMemberColor getColor(){
        return color;
    }

    public int getValue(){
        return  value;
    }
}
