package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;

/**
 * Created by higla on 20/05/2017.
 */
public class CouncilAS extends AbstractActionSpace {
    //ArrayList<ImmediateEffectInterface> effects;
    /**
     * this method lets you perform an action
     *
     * @param familyMember
     */
    @Override
    public void performAction(FamilyMember familyMember) {

    }
    public String getSpaceDescription(){
        int i;
        String temp = new String();
        for(i=0; i<super.getEffects().size(); i++)
            temp += " " + super.getEffects().get(i).descriptionShortOfEffect();
    return temp;
    }
}
