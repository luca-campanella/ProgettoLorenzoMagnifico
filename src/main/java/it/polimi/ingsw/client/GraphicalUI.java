package it.polimi.ingsw.client;

/**
 * Created by higla on 11/05/2017.
 */

import it.polimi.ingsw.utils.Debug;

        import java.util.Scanner;

public class GraphicalUI extends AbstractUIType{
    //TODO GUI -- I copied CLI here because absUIType wouldn't let me run the program and test it
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
        clientMain.callbackFamilyMemberSelected(familyColorID);
    }
    /**
     * this method prints all allowed actions for the user.
     */
    public void printAllowedActions(){
        Debug.printDebug("Sono in CLI.printAllowedActions()");
        System.out.println("Stampo tutte le azioni disponibili dell'utente");
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
    public GraphicalUI(ClientMain x)
    {
        this.clientMain =  x;
    }
    /**
     * Chiede all'utente con quale connessione si vuole connettere
     */
    public void askNetworkType()
    {
        Debug.printError("Sono nella GUI. Voglio chedere quale Network usare.");

    }

    /**
     * This is the method which starts asking the User inputs.
     */
    public void readAction(){
        Debug.printError("Sono nella GUI.readAction");
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
        System.out.println("Sono in GUI");
    }

}

