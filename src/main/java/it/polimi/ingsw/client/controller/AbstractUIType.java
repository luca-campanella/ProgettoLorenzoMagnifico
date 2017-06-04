package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.UIControllerUserInterface;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeSomethingEffect;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by higla on 11/05/2017.
 */
//TODO classe astratta che mi rende trasparente l'uso di CLI / gui

abstract public class AbstractUIType {
    UIControllerUserInterface UIController = new UIControllerUserInterface();

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
     * This method is called when a choice on a council gift should be perfomed by the ui
     * @param options
     * @return the index of the selected option, the choice the user made
     */
    public abstract int askCouncilGift(ArrayList<TakeSomethingEffect> options);

    /**
     * This method is called when a choice on which effect to activate in a yellow card should be perfomed by the ui
     * @param possibleEffectChoices
     * @return the index of the chosen effect
     */
    public abstract int askYellowBuildingCardEffectChoice(ArrayList<ImmediateEffectInterface> possibleEffectChoices);
}

