package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;

/**
 * This class is the abstract generalization of an Action Space, a place were a family member can be placed
 */
public abstract class AbstractActionSpace {

    /**
     *it's the list of family members on this place
     */
    protected ArrayList<FamilyMember> familyMembers;
    /**
     * the value the {@link FamilyMember} needs to be able to be put into the action space
     */
    protected int diceRequirement;

    /**
     * The effects associated with the ActionSpace
     */
    protected ArrayList<ImmediateEffectInterface> effects;

    protected AbstractActionSpace() {
        effects = new ArrayList<>(1);
    }


    /*
     * this method lets you perform the actions corresponding to the action space
     * @param familyMember the family member to perform the action with
     * @param choicesController the controller fo choices in case the action has choices the user should make
     */
   public void performAction(FamilyMember familyMember, ChoicesHandlerInterface choicesController){
       ;
   }
//todo decide either of this methods
    public void performAction(FamilyMember familyMember) {
        ;
    }


    public int getDiceValue() {
        return diceRequirement;
    }

    public void setDiceValue(int diceValue) {
        this.diceRequirement = diceValue;
    }

    /**
     * this method returns all immediate effects of a card
     * @return
     */
    public ArrayList<ImmediateEffectInterface> getEffects() {
        return effects;
    }

    /**
     * Adds just one effect to the ArrayList of effects associated with the action space
     * @param effect the effect to add
     */
    public void addEffect(ImmediateEffectInterface effect) {
        effects.add(effect);
    }

    /**
     * Mathod used for debugging
     * @return a short description of the effects
     */
    public String getEffectShortDescription()
    {
        String desc = new String();

        for(ImmediateEffectInterface i : effects)
            desc += i.descriptionShortOfEffect() + " ";

        return desc;
    }

    /**
     * Method used for debugging
     * @return a description of the effects
     */
    public String getEffectDescription()
    {
        String desc = new String();

        for(ImmediateEffectInterface i : effects)
            desc += i.descriptionOfEffect() + " | ";

        return desc;
    }

}
