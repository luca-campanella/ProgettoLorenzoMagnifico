package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.FamilyMember;

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
   private boolean first = true;
    public HarvestAS() {
        super();
    }

    public HarvestAS(int diceRequirement, int valueMalus, boolean first) {
        super(diceRequirement);
        this.valueMalus = valueMalus;
        this.first = first;
        this.twoPlayersOneSpace = false;
    }

    public void performAction(FamilyMember familyMember)
    {
        //TODO
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

    public int getValueNeeded(){

        if(getOccupyingFamilyMemberNumber() > 0)
            return valueMalus+valueStandard;
        return valueStandard;
    }

    public boolean isTwoPlayersOneSpace() {
        return twoPlayersOneSpace;
    }

    public void setTwoPlayersOneSpace(boolean twoPlayersOneSpace) {
        this.twoPlayersOneSpace = twoPlayersOneSpace;
    }


}
