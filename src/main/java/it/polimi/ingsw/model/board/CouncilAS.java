package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
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
    ArrayList<ImmediateEffectInterface> effectsOnPlacement;
    ArrayList<GainResourceEffect> councilGiftChoices;

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

    /**
     * this method gives a description of the action space
     * @return
     */
    public String getSpaceDescription(){
        int i;
        String temp = new String();
        for(i=0; i<super.getEffects().size(); i++)
            temp += " " + super.getEffects().get(i).descriptionShortOfEffect();
    return temp;
    }

    /**
     * this method
     * @returns all family member insde the council
     */
    public ArrayList<FamilyMember> getFamilyMembers(){
        return familyMembers;

    }

    /**
     * delete all the family members on this space
     */
    public void clearCouncil(){

        familyMembers.clear();

    }

    public ArrayList<GainResourceEffect> getCouncilGiftChoices() {
        return councilGiftChoices;
    }

    public void setCouncilGiftChoices(ArrayList<GainResourceEffect> councilGiftChoices) {
        this.councilGiftChoices = councilGiftChoices;
    }
}
