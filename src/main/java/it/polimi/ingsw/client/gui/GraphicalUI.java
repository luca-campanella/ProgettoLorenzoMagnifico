package it.polimi.ingsw.client.gui;

/**
 * Created by higla on 11/05/2017.
 */

import it.polimi.ingsw.client.controller.AbstractUIType;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.client.controller.ControllerCallbackInterface;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GraphicalUI extends AbstractUIType {
    //TODO gui -- I copied CLI here because absUIType wouldn't let me run the program and test it
    public void selectFamilyMember()
    {
        int i;
        String familyColorID;
        System.out.print("Select a family member. You can choose " );
        /*for(i=0; i< familyMembers.size(); i++)
            System.out.print(familyMembers.... + " ");
        */
        System.out.print("Yellow, Red, Green, Neutral");
        while(true) {
            familyColorID = inputScanner.nextLine();
            if(existingColors(familyColorID))
                break;
        }
        //clientMain.callbackFamilyMemberSelected(familyColorID);
    }
    /**
     * this method prints all allowed actions for the user.
     */
    public void printAllowedActions(){
        Debug.printDebug("Sono in CLI.printAllowedActions()");
        System.out.println("Stampo tutte le azioni disponibili dell'utente");
    }

    @Override
    public int askChoice(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer) {
        return 0;
    }

    /**
     * This method is called when the player has joined a room, but the game isn't started yet
     */
    @Override
    public void showWaitingForGameStart() {
        
    }

    /**
     * Used when it's the turn of the user and he has to choose which action he wants to perform
     * This method will trigger either
     * {@link ControllerCallbackInterface#callbackFamilyMemberAndServantsSelected(FamilyMember)} (it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum, int)} or
     * //todo other methods triggered
     *
     * @param playableFMs the list of playable family members to make the user choose
     */
    @Override
    public void askInitialAction(ArrayList<FamilyMember> playableFMs) {

    }

    /**
     * This method is called when a choice on a council gift should be perfomed by the ui
     *
     * @param options
     * @return the index of the selected option, the choice the user made
     */
    @Override
    public int askCouncilGift(ArrayList<GainResourceEffect> options) {
        return 0;
    }

    /**
     * This method is called when a choice on which effect to activate in a yellow card should be perfomed by the ui
     *
     * @param possibleEffectChoices
     * @return the index of the chosen effect
     */
    @Override
    public int askYellowBuildingCardEffectChoice(ArrayList<ImmediateEffectInterface> possibleEffectChoices) {
        return 0;
    }

    /**
     * This method is called when a choice on which cost to pay in a purple card should be perfomed by the ui
     *
     * @param costChoiceResource the list of resources the player will pay if he chooses this option
     * @param costChoiceMilitary the cost he will pay on something conditioned
     * @return 0 if he chooses to pay with resources, 1 with military points
     */
    @Override
    public int askPurpleVentureCardCostChoice(List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
        //todo
        return 0;
    }

    /**
     * this method just alerts user that there was an error somewhere. It doesn't handle the error
     *
     * @param error
     */
    @Override
    public void printError(String error) {
        //TODO implent
    }

    /**
     * This method is called by {@link ClientMain} to display an incoming chat message (Direction: {@link ClientMain} -> {@link AbstractUIType}; general direction: Server -> Client)
     *
     * @param senderNick
     * @param msg
     */
    @Override
    public void displayChatMsg(String senderNick, String msg) {

    }

    @Override
    public void askChatMsg() {

    }

    //@Override
    public int askChoice(String nameCard, ArrayList<String> choices) {
        return 0;
    }

    /**
     * this method helps selectFamilyMember()'s method return if the color user wrote is right or not
     * this method should also receive the familyMembers list to match the input.
     * @param familyColorID
     * @return
     */
    private boolean existingColors(String familyColorID){
        return (familyColorID.equalsIgnoreCase("yellow")||familyColorID.equalsIgnoreCase("red")||familyColorID.equalsIgnoreCase("green")||familyColorID.equalsIgnoreCase("neutral"));
    }


    String tmpInput;
    Scanner inputScanner = new Scanner(System.in);
    ClientMain clientMain;
    // UIControllerUserInterface UIController = new UIControllerUserInterface();
    public void loginFailure(String reasonFailure)
    {

        //System.out.println("Error: " + reasonFailure)
        ;
        //askLoginOrCreate()
    }


    /**
     * This is the costructor of the class
     * @param x is the ClientMain
     */
    public GraphicalUI(ControllerCallbackInterface x)
    {
       super(x);
    }
    /**
     * Chiede all'utente con quale connessione si vuole connettere
     */
    public void askNetworkType()
    {
        Debug.printError("Sono nella gui. Voglio chedere quale network usare.");

    }

    /**
     * This is the method which starts asking the User inputs.
     */
    public void readAction(){
        Debug.printError("Sono nella gui.readAction");
        while(true)
        {
            System.out.println("Quale azione vuoi fare? Giocare un Leader, Scartare un Leader, Piazzare un Familiare ? Scrivi Gioca, Scarta, Piazza");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("gioca")){
                clientMain.callbackPlayLeader();
                break;
            }
            if(tmpInput.equalsIgnoreCase("scarta")){
                clientMain.callbackDiscardLeader();
                break;
            }
            if(tmpInput.equalsIgnoreCase("Piazza")){
                clientMain.callbackPerformPlacement();
                break;
            }

        }
    }

    /**
     * This method asks to the user if he wants to connect with an existing account or to create one.
     */
    public void askLoginOrCreate()
    {
        //TODO gophical
    }

    //permette all'utente di create un nuovo account
    public void createNewAccount(){
        System.out.println("Creating new Account...");
    }
    //permette all'utente di Loggare
    public void askLogin(){
        System.out.println("Logging In...");
    }
    //aggiorna l'UI
    public void updateView()
    {
        System.out.println("Sono in gui");
    }

}

