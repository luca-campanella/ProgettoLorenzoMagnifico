package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * this class is the place where the player can place the family member to harvest
 */
public class HarvestAS extends AbstractActionSpace implements Serializable{

    private boolean twoPlayersOneSpace;
    //It's the minimum dice value of the family member
    private int valueStandard;
    //it's the malus that family member has if it isn't the first
    private int valueMalus;
    //it's the list of family members on this place

   // private ArrayList<FamilyMember> familyMembers;
   //private boolean first = true;
    public HarvestAS() {
        super();
    }

    public HarvestAS(int diceRequirement, int valueMalus) {
        super(diceRequirement);
        this.valueMalus = valueMalus;
        this.twoPlayersOneSpace = false;
    }

    /*public void performAction(FamilyMember familyMember)
    {
        //TODO
    }*/

    /**
     * this method checks if the player that put here a family member is the first
     * @return
     */
    public boolean checkIfFirst(){
        if(getOccupyingFamilyMemberNumber() == 0)
            return true;
        return false;
    }

    public int getValueStandard() {
        return valueStandard;
    }

    public void setValueStandard(int valueStandard) {
        this.valueStandard = valueStandard;
    }

    public int getValueMalus() {
        return valueMalus;
    }

    public void setValueMalus(int valueMalus) {
        this.valueMalus = valueMalus;
    }

    /**
     * this method returns the right build value
     * With the parameter checks also the bonus on leaders
     * @param canPlaceOccupiedSpace to check if the player has a leader with this type of ability
     * @return
     */
    public int getValueNeeded(boolean canPlaceOccupiedSpace){

        if(checkIfFirst())
            return getDiceRequirement();
        if(canPlaceOccupiedSpace)
            //return getDiceRequirement();
            return valueMalus+getDiceRequirement();
        //todo check this solution here - if you modify this you need also to modify the test
        return 10;
    }

    /**
     * @return the fact that it is a 2 players game
     */
    public boolean isTwoPlayersOneSpace() {
        return twoPlayersOneSpace;
    }

    public void setTwoPlayersOneSpace(boolean twoPlayersOneSpace) {
        this.twoPlayersOneSpace = twoPlayersOneSpace;
    }


}
