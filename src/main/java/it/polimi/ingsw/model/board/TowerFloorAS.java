package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.utils.Debug;

/**
 * This action space is the one placed on the tower, with a corresponding card to it
 */
public class TowerFloorAS extends AbstractActionSpace {

    private int diceCost;

    private ImmediateEffectInterface effect;

    private AbstractCard card;

    private FamilyMember familyMember;

    public TowerFloorAS(int diceCost, ImmediateEffectInterface effect){
        this.diceCost = diceCost;
        this.effect = effect;
    }

    public void placeCard(AbstractCard card){
        this.card = card;
        clearFloor();
    }

    public AbstractCard getCard(){
        return card;
    }

    public void performAction(FamilyMember familyMember){

        this.doEffect();
        this.familyMember = familyMember;
    }

    private void clearFloor(){
        this.familyMember = null;
    }

    public int getDiceCost(){
        return diceCost;
    }

    public FamilyMember getFamilyMember(){
        return  familyMember;
    }

    private void doEffect(){
        Debug.printVerbose("Stampo l'effetto " + getEffects());
    }


}
