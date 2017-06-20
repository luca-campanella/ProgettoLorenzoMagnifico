package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * This action space is the build action space.
 */
public class BuildAS extends AbstractActionSpace implements Serializable {
    // this boolean checks ?
    private boolean twoPlayersOneSpace;

    //this is the standard malus build value. (ex -3)
    int valueMalus = 0;
    //this boolean check if the player that puts there the family member is the first
    //todo Non lo dovrebbe fare il controller? -- Arto
    //private boolean first = true;
    public BuildAS() {
        super();
    }

    public BuildAS(int diceRequirement, int valueMalus) {
        super(diceRequirement);
        this.valueMalus = valueMalus;
        //this.first = first;
        this.twoPlayersOneSpace = false;
    }

    /*
     * This methods updates all the resources player has giving his yellow cards
     */
    /*@Override
    public void performAction(FamilyMember familyMember) {
        boolean trueIfMalus;
        trueIfMalus = checkIfFirst();
        if(trueIfMalus)
            //chiama l'effetto con il valore -3
        ;
        //altrimenti chiama la funzione con il valore +0
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
            return getDiceRequirement();

        return valueMalus+getDiceRequirement();
    }

    public boolean isTwoPlayersOneSpace() {
        return twoPlayersOneSpace;
    }

    public void setTwoPlayersOneSpace(boolean twoPlayersOneSpace) {
        this.twoPlayersOneSpace = twoPlayersOneSpace;
    }

}

