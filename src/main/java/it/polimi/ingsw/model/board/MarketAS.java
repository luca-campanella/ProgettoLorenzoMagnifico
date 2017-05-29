package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;

/**
 * this class is the place on the market where the player can place the family member
 */
public class MarketAS extends AbstractActionSpace{

    public MarketAS() {
        super();
    }

    //It's the minimum dice value of the family member
    private int valueStandard;
    //it's the list of family members on this place
    private FamilyMember familyMember;

    private void placeFamilyMember(FamilyMember familyMember){
        this.familyMember = familyMember;
        performAction(familyMember);
    }

    public FamilyMember getFamilyMember(){
        return  familyMember;
    }

    public MarketAS(int valueStandard) {
        super();
        this.valueStandard = valueStandard;
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
