package it.polimi.ingsw.client;

/**
 * Created by higla on 11/05/2017.
 */
import it.polimi.ingsw.utils.Debug;

import java.util.*;
//TODO
public class CommandLineUI extends AbstractUIType{

    String tmpInput;
    Scanner inputScanner = new Scanner(System.in);
    ClientMain clientMain;
    // UIControllerUserInterface UIController = new UIControllerUserInterface();


    /**
     * This is the costructor of the class
     * @param clientMain is the clientMain
     */
    public CommandLineUI(ClientMain clientMain)
    {
        this.clientMain =  clientMain;
    }
    /**
     * This method ask uses which network type wants to use.
     */
    public void askNetworkType()
    {
        Debug.printDebug("I am in CLI. Select NetworkType");
        NetworkTypeEnum choice;
        while(true)
        {
            System.out.println("Choose RMI or Socket.");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("RMI")) {
            choice = NetworkTypeEnum.RMI;
            break;
        }
            if(tmpInput.equalsIgnoreCase("SOCKET")) {
            choice = NetworkTypeEnum.SOCKET;
            break;
        }

        }
        clientMain.callbackNetworkType(choice);
    }

    /**
     * This is the method which starts asking the User inputs.
     */
    public void readAction(){
        Debug.printDebug("I'm in CLI.readAction");
        while(true)
        {
            System.out.println("What action do you wanna make? Play a Leader, Discard a Leader, Place a family member? Write Play, Discard, Place");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("Play")){
                clientMain.callbackPlayLeader();
                break;
            }
            if(tmpInput.equalsIgnoreCase("Discard")){
                clientMain.callbackDiscardLeader();
                break;
            }
            if(tmpInput.equalsIgnoreCase("Place")){
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
        Debug.printDebug("I am in CLI. I ask user if he wants to Login or Create a new account");
        while(true)
        {
            System.out.println("Do you want to Create a new account, or LogIn into an old one? Write Create, or Login");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("Create")){
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
        System.out.println("Aggiorno la view");
    }

    /**
     * this method handles the login failure
     * @param reasonFailure
     */
    public void loginFailure(String reasonFailure)
    {

        System.out.println("Error: " + reasonFailure);
        //askLoginOrCreate()
    }

}

