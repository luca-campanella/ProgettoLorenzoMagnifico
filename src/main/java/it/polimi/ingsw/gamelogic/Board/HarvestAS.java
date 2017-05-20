package it.polimi.ingsw.gamelogic.Board;

import it.polimi.ingsw.gamelogic.Player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public class HarvestAS extends AbstractActionSpace{
    //It's the minimum dice value of the family member
    int valueStandard;
    //it's the malus that family member has if it isn't the first
    int valueMalus;
    public void performAction(FamilyMember familyMember)
    {
        //TODO
        ;
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
}
