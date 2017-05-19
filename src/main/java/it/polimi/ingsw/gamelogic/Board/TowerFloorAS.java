package it.polimi.ingsw.gamelogic.Board;

import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public class TowerFloorAS extends AbstractActionSpace {

    /*public TowerFloorAS(int diceValue, EffectInterface effect)
    {
    this.diceValue = diceValue;
    this.effect = effect;
    }*/
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
