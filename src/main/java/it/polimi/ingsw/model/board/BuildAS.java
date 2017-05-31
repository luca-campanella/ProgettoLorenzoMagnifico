package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;

/**
 * Created by higla on 16/05/2017.
 */
public class BuildAS extends AbstractActionSpace {
    private boolean twoPlayersOneSpace;
    int valueStandard = 0;
    int valueMalus = 0;
    private boolean first = true;
    //it's the list of family members on this place
    private ArrayList<FamilyMember> familyMembers;
    public BuildAS() {
        super();
    }

    public BuildAS(int valueStandard, int valueMalus, boolean first) {
        super();
        this.valueStandard = valueStandard;
        this.valueMalus = valueMalus;
        this.first = first;
        familyMembers = new ArrayList<>(8);
        this.twoPlayersOneSpace = false;
    }

    /**
     * This methods updates all the resources player has giving his yellow cards
     */
    @Override
    public void performAction(FamilyMember familyMember) {
        boolean trueIfMalus;
        trueIfMalus = checkIfFirst();
        if(trueIfMalus)
            //chiama l'effetto con il valore -3
        ;
        //altrimenti chiama la funzione con il valore +0
    }

    /**
     * this method checks if the player that put here a family member is the first
     * @return
     */
    private boolean checkIfFirst(){
        if(first == true)
        {
            first = false;
            return true;
        }
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

    public ArrayList<FamilyMember> getFamilyMembers(){
        return  familyMembers;
    }

    /**
     * this method returns the right build value
     * @return
     */
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

    public void clearBuild(){

        familyMembers.clear();

    }
}

