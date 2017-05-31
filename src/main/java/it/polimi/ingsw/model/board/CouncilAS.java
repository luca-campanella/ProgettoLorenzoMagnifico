package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;

/**
 * this space is used to choose the order of the players' turn
 */
public class CouncilAS extends AbstractActionSpace {

    /**
     * the list of family member on this space
     */
    private ArrayList<FamilyMember> familyMembers;
    //ArrayList<ImmediateEffectInterface> effects;

    public CouncilAS(){

        familyMembers = new ArrayList<>(5);

    }
    /**
     * this method lets you perform an action
     *
     * @param familyMember
     */
    @Override
    public void performAction(FamilyMember familyMember) {

        familyMembers.add(familyMember);

    }

    public String getSpaceDescription(){
        int i;
        String temp = new String();
        for(i=0; i<super.getEffects().size(); i++)
            temp += " " + super.getEffects().get(i).descriptionShortOfEffect();
    return temp;
    }


    public ArrayList<FamilyMember> getFamilyMembers(){

        return familyMembers;

    }

    /**
     * delete all the family members on this space
     */
    public void clearSpace(){

        familyMembers.clear();

    }
}
