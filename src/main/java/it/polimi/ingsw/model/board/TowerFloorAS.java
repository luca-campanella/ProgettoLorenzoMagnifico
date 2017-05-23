package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.utils.Debug;

/**
 * This action space is the one placed on the tower, with a corresponding card to it
 */
public class TowerFloorAS extends AbstractActionSpace {

    //TODO:
    public void performAction(FamilyMember familyMember){
        this.doEffect();
    }

    private void doEffect(){
        Debug.printVerbose("Stampo l'effetto " + getEffects());
    }


}
