package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.FamilyMember;

/**
 * This action space is the one placed on the tower, with a corresponding card to it
 */
public class TowerFloorAS extends AbstractActionSpace {

    public void performAction(FamilyMember familyMember){
        this.doEffect();
    }
    private void doEffect(){
        System.out.println("Stampo l'effetto " + getEffects());
    }


}
