package it.polimi.ingsw.gamelogic.Board;

import it.polimi.ingsw.gamelogic.Player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public class BuildAS extends AbstractActionSpace {
    int malus;
    boolean first = true;
    BuildAS(int malus){
        this.malus = malus;

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
}
