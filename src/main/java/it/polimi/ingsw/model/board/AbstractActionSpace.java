package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;

/**
 * This class is the abstract generalization of an Action Space, a place were a family member can be placed
 */
public abstract class AbstractActionSpace {

    /**
     *it's the list of family members on this place
     */
    private ArrayList<FamilyMember> familyMembers;
    /**
     * the value the {@link FamilyMember} needs to be able to be put into the action space
     */
    private int diceRequirement;

    /**
     * The effects associated with the ActionSpace
     */
    private ArrayList<ImmediateEffectInterface> effects;

    protected AbstractActionSpace() {
        effects = new ArrayList<>(1);
        familyMembers = new ArrayList<>(8);
    }

    public AbstractActionSpace(int diceRequirement) {
        this();
        this.diceRequirement = diceRequirement;
        familyMembers = new ArrayList<>(8);
    }

    /*
     * this method lets you perform the actions corresponding to the action space
     * @param familyMember the family member to perform the action with
     * @param choicesController the controller fo choices in case the action has choices the user should make
     */
   /*public abstract void performAction(FamilyMember familyMember, ChoicesHandlerInterface choicesController);  */
   //Implemented only int he AS that really perform actions

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

    public int getDiceRequirement() {
        return diceRequirement;
    }

    public void setDiceRequirement(int diceRequirement) {
        this.diceRequirement = diceRequirement;
    }

    public void addFamilyMember(FamilyMember familyMember){
        familyMembers.add(familyMember);
    }

    /**
     * This method returns the number of family members placed in the action space
     * @return the number of family members placed in the action space
     */
    public int getOccupyingFamilyMemberNumber() {
        return familyMembers.size();
    }

    /**
     * Returns the list of family members inside the action space, should be used with care
     * @return the arrylist of family members
     */
    public ArrayList<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void clearAS(){
        familyMembers.clear();
    }

    /**
     * This method is used by subclasses inside perform action in order to set the family member as played and
     * subtract the servants needed to perform that action
     * @param familyMember the family member on current action
     */
    protected void playFMandSubServantsToPlayer(FamilyMember familyMember) {
        Player player = familyMember.getPlayer();
        //set the family member as used in the player
        player.playFamilyMember(familyMember);
        //subtract corresponding family members needed
        int servantsNeeded = getDiceRequirement() - familyMember.getValue();
        if(servantsNeeded<0)
            servantsNeeded=0;
        player.subResource(new Resource(ResourceTypeEnum.SERVANT, servantsNeeded));
    }
}
