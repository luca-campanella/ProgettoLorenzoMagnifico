package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public class BuildAS extends AbstractActionSpace {
    int valueStandard = 0;
    int valueMalus = 0;
    private boolean first = true;
    public BuildAS() {
        super();
    }

    public BuildAS(int valueStandard, int valueMalus, boolean first) {
        super();
        this.valueStandard = valueStandard;
        this.valueMalus = valueMalus;
        this.first = first;
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
