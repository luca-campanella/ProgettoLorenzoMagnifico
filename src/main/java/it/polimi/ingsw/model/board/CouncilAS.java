package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * this space is used to choose the order of the players' turn
 */
public class CouncilAS extends AbstractActionSpace implements Serializable{

    ArrayList<GainResourceEffect> councilGiftChoices;

    public CouncilAS(){
        super();
    }


    /**
     * this method gives a description of the action space
     * @return
     */
    public String getSpaceDescription(){
        int i;
        StringBuilder temp = new StringBuilder("");
        for(i=0; i<super.getEffects().size(); i++)
            temp.append(super.getEffects().get(i).descriptionShortOfEffect());
    return temp.toString();
    }
    public void setCouncilGiftChoices(ArrayList<GainResourceEffect> councilGiftChoices){
        this.councilGiftChoices = councilGiftChoices;
    }
    public List<GainResourceEffect> getCouncilGiftChoices() {
        return new ArrayList<>(councilGiftChoices);
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
