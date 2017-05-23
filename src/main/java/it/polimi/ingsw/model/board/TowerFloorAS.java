package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.effects.EffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public class TowerFloorAS extends AbstractActionSpace {

    public void performAction(FamilyMember familyMember){
        this.doEffect();
    }
    private void doEffect(){
        System.out.println("Stampo l'effetto " + getEffect());
    }

    public void setEffect(EffectInterface effect)
    {
       this.effect = effect;
    }

}
