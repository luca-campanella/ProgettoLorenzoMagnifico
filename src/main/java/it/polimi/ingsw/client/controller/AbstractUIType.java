package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.cli.CommandLineUI;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the abstract representation of the user interface
 * It can either be implemented by {@link CommandLineUI} or by {@link it.polimi.ingsw.client.gui.GraphicalUI}
 */
abstract public class AbstractUIType {

    private ControllerCallbackInterface controller;

    public AbstractUIType(ControllerCallbackInterface controller) {
        this.controller = controller;
    }

    protected ControllerCallbackInterface getController() {
        return controller;
    }

    //This method read an action from the user
    abstract public void readAction();
    //This method ask what network the user wants to use
    abstract public void askNetworkType();
    //This method ask the user if he wants to login or to create a new account
    abstract public void askLoginOrCreate();
    //This method allows the user to create a newAccount
    abstract public void createNewAccount();
    //This method allows the user to ask to Login
    abstract public void askLogin();
    //This method updates the view
    abstract public void updateView();
    //Highlights that login has failed and manages that
    abstract public void loginFailure(String failureReason);
    //Selects a family member
    abstract public void selectFamilyMember();
    //This method prints allowed actions
    abstract public void printAllowedActions();

    /**
     * this method just alerts user that there was an error somewhere. It doesn't handle the error
     */
    abstract public void printError(String error);

    /**
     * This method is called by {@link ClientMain} to display an incoming chat message (Direction: {@link ClientMain} -> {@link AbstractUIType}; general direction: Server -> Client)
     * @param senderNick
     * @param msg
     */
    public abstract void displayChatMsg(String senderNick, String msg);

    //TODO this is a method just for testing chat
    public abstract void askChatMsg();


    public abstract int askChoice(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer);

    /**
     * This method is called when the player has joined a room, but the game isn't started yet
     */
    public abstract void showWaitingForGameStart();

    /**
     * Used when it's the turn of the user and he has to choose which action he wants to perform
     * This method will trigger either
     * {@link ControllerCallbackInterface#callbackFamilyMemberAndServantsSelected(DiceAndFamilyMemberColorEnum, int)} or
     * //todo other methods triggered
     * @param playableFMs the list of playable family members to make the user choose
     */
    public abstract void askInitialAction(ArrayList<FamilyMember> playableFMs);

    /**
     * This method is called when a choice on a council gift should be perfomed by the ui
     * @param options
     * @return the index of the selected option, the choice the user made
     */
    public abstract int askCouncilGift(ArrayList<GainResourceEffect> options);

    /**
     * This method is called when a choice on which effect to activate in a yellow card should be perfomed by the ui
     * @param possibleEffectChoices
     * @return the index of the chosen effect
     */
    public abstract int askYellowBuildingCardEffectChoice(ArrayList<ImmediateEffectInterface> possibleEffectChoices);

    /**
     * This method is called when a choice on which cost to pay in a purple card should be perfomed by the ui
     * @param costChoiceResource the list of resources the player will pay if he chooses this option
     * @param costChoiceMilitary the cost he will pay on something conditioned
     * @return 0 if he chooses to pay with resources, 1 with military points
     */
    public abstract int askPurpleVentureCardCostChoice(List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary);

}

