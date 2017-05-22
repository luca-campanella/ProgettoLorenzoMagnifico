package it.polimi.ingsw.gamelogic.Board;

import it.polimi.ingsw.gamelogic.player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public class BuildAS extends AbstractActionSpace {
    int valueStandard;
    int valueMalus;
    private boolean first = true;
    public BuildAS(){
        ;
    }
    @Override
    /**
     * This methods updates all the resources player has giving his yellow cards
     */
    public void performAction(FamilyMember familyMember) {
        boolean trueIfMalus;
        trueIfMalus = checkIfFirst();
        if(trueIfMalus)
            //chiama l'effetto con il valore -3
        ;
        //altrimenti chiama la funzione con il valore +0
    }

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
}
