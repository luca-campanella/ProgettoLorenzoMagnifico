package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Dice;

/**
 * the family member owned by a player
 */
public class FamilyMember {

    /**
     * the dice that the family member is linked
     */
    private Dice dice;

    /**
     * color of the family member
     */
    private DiceAndFamilyMemberColor color;

    /**
     * player that owned this family member
     */
    private Player player;

    public FamilyMember(Dice dice, Player player){

        this.player= player;
        this.dice=dice;
        color= dice.getColor();

    }

    /**
     * this method is used to know the value of the family member
     * @return the value of the dice
     */
    public int getValue(){

        return dice.getValue();

    }

    public DiceAndFamilyMemberColor getColor(){

        return color;

    }

    public Player getPlayer(){

        return player;

    }
}
