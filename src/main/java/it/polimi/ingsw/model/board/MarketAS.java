package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.FamilyMember;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class is the place on the market where the player can place the family member
 */
public class MarketAS extends AbstractActionSpace implements Serializable{

    public MarketAS() {
        super();
    }

    //It's the minimum dice value of the family member
    private int valueStandard;
    //it's the list of family members on this place
    //private FamilyMember familyMember;

    private void placeFamilyMember(FamilyMember familyMember){
        familyMembers.add(familyMember);
        performAction(familyMember);
    }

    public FamilyMember getFamilyMember(){
        return familyMembers.get(0);
    }
    /*
    public MarketAS(int valueStandard) {
        super();
        this.valueStandard = valueStandard;
    }*/

    /**
     * it clears the market removing the family member
     */
    public void clearMarket(){

        familyMembers = null;

    }

    public int getValueStandard() {
        return valueStandard;
    }

    public void setValueStandard(int valueStandard) {
        this.valueStandard = valueStandard;
    }

    public void performAction(FamilyMember familyMember){

        placeFamilyMember(familyMember);
        //TODO
    }

}
