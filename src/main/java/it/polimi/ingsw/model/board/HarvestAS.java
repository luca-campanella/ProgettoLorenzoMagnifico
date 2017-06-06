package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.FamilyMember;

import java.io.Serializable;
import java.util.ArrayList;

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
    private ArrayList<FamilyMember> familyMembers;

    public HarvestAS() {
        super();
    }

    public void placeFamilyMember(FamilyMember familyMember){
        familyMembers.add(familyMember);
    }

    public ArrayList<FamilyMember> getFamilyMembers(){
        return  familyMembers;
    }

    public HarvestAS(int valueStandard, int valueMalus) {
        super();
        familyMembers = new ArrayList<>(8);
        this.valueStandard = valueStandard;
        this.valueMalus = valueMalus;
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

        if(familyMembers.size()>0)
            return valueMalus+valueStandard;
        return valueStandard;
    }

    public void addFamilyMember(FamilyMember familyMember){
        familyMembers.add(familyMember);
    }
    public boolean isTwoPlayersOneSpace() {
        return twoPlayersOneSpace;
    }

    public void setTwoPlayersOneSpace(boolean twoPlayersOneSpace) {
        this.twoPlayersOneSpace = twoPlayersOneSpace;
    }

    /**
     * deletes all the family member on the space
     */
    public void clearHarvest(){

        familyMembers.clear();

    }

}
