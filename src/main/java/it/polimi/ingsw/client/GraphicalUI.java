package it.polimi.ingsw.client;

/**
 * Created by higla on 11/05/2017.
 */

import it.polimi.ingsw.utils.Debug;

        import java.util.Scanner;

public class GraphicalUI extends AbstractUIType{
    //TODO GUI -- I copied CLI here because absUIType wouldn't let me run the program and test it

    String tmpInput;
    Scanner inputScanner = new Scanner(System.in);
    ClientMain clientMain;
    // UIControllerUserInterface UIController = new UIControllerUserInterface();


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
        Debug.printError("Sono nella GUI. Voglio chedere all'utente se creare un nuovo account o loggarsi a uno già esistente.");
        while(true)
        {
            System.out.println("Vuoi CREARE un account o fare il LogIn a uno già esistente? Nel primo caso scrivi crea, nel secondo LogIn");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("Crea")){
                clientMain.callbackCreateAccount();
                break;
            }

            if(tmpInput.equalsIgnoreCase("LogIn")){
                clientMain.callbackLogin();
                break;
            }
        }
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

