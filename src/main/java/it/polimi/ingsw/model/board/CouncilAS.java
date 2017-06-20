package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.player.FamilyMember;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this space is used to choose the order of the players' turn
 */
public class CouncilAS extends AbstractActionSpace implements Serializable{

    ArrayList<GainResourceEffect> councilGiftChoices;

    public CouncilAS(){
        super();
    }
    /**
     * this method lets you perform an action
     *
     * @param familyMember
     */
    //@Override
    public void performAction(FamilyMember familyMember) {

        getFamilyMembers().add(familyMember);

    }

    /**
     * this method gives a description of the action space
     * @return
     */
    public String getSpaceDescription(){
        int i;
        String temp = "";
        for(i=0; i<super.getEffects().size(); i++)
            temp += " " + super.getEffects().get(i).descriptionShortOfEffect();
    return temp;
    }

    public ArrayList<GainResourceEffect> getCouncilGiftChoices() {
        return councilGiftChoices;
    }

    public void setCouncilGiftChoices(ArrayList<GainResourceEffect> councilGiftChoices) {
        this.councilGiftChoices = councilGiftChoices;
    }

    /**
     * This method performs the real action on the model when the player places a FM in the council
     * @param familyMember the family member to perform the action with
     * @param choicesController needed because there can be some decisions tied to the action
     */
    //@Override
    public void performAction(FamilyMember familyMember, ChoicesHandlerInterface choicesController)
    {
        playFMandSubServantsToPlayer(familyMember);
        addFamilyMember(familyMember);
        getEffects().forEach(effect -> effect.applyToPlayer(familyMember.getPlayer(), choicesController, "CouncilAS"));
    }
}
