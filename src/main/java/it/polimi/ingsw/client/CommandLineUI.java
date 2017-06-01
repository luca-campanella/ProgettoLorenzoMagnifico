package it.polimi.ingsw.client;

/**
 * to insert inputs on the cli
 */

import it.polimi.ingsw.client.controller.AbstractUIType;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.client.controller.datastructure.UsrPwdContainer;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
//TODO
public class CommandLineUI extends AbstractUIType {

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
            System.out.println("Choose rmi or socket.");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("rmi")) {
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
     * this method allows the user to select a familyMember
     */
    public void selectFamilyMember()
    {
        int i;
        String familyColorID;
        System.out.print("Select a family member. You can choose " );
        /*for(i=0; i< familyMembers.size(); i++)
            System.out.print(familyMembers.... + " ");
        */
        System.out.print("Yellow, Red, Green, Neutral");
        System.out.println();
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
        System.out.println("I wanna print all the actions available");
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

    /**
     * This method asks to the user if he wants to connect with an existing account or to create one.
     */
    public void askLoginOrCreate()
    {
        Debug.printDebug("I am in CLI.askLoginOrCreate");
        String userID = "user";
        String userPW = "password";
        UsrPwdContainer usrAndPwd;
        while(true)
        {
            System.out.println("Do you want to Create a new account, or LogIn into an old one? Write Create, or Login");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("Create")){
                usrAndPwd = readUsrPwd();
                clientMain.callbackCreateAccount(usrAndPwd.getNickname(), usrAndPwd.getPassword());
                break;
            }

            if(tmpInput.equalsIgnoreCase("LogIn")){
                usrAndPwd = readUsrPwd();
                clientMain.callbackLogin(usrAndPwd.getNickname(), usrAndPwd.getPassword());
                break;
            }
            /*
            //TODO eliminate skip
            if(tmpInput.equalsIgnoreCase("Skip")){
                clientMain.callbackLoginAsGuest();
                break;
            }*/
        }
    }

    private UsrPwdContainer readUsrPwd()
    {
        String nickname, password;

        System.out.println("Insert UserId");
        nickname = inputScanner.nextLine();
        System.out.println("Insert PassWord");
        password = inputScanner.nextLine();

        return new UsrPwdContainer(nickname, password);
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

    public void printError(String error)
    {
        System.out.println( error );
    }

    /**
     * This method is called by {@link ClientMain} to display an incoming chat message (Direction: {@link ClientMain} -> {@link AbstractUIType}; general direction: Server -> Client)
     *
     * @param senderNick
     * @param msg
     */
    @Override
    public void displayChatMsg(String senderNick, String msg) {
        //TODO something more visually appealing
        System.out.println("<" + senderNick + ">: " + msg);
    }

    //TODO this is a method just for testing chat
    @Override
    public void askChatMsg() {
        System.out.println("Please insert chat msg: ");

        try {
            clientMain.callbackSendChatMsg(inputScanner.nextLine());
        } catch (NetworkException e) {
            Debug.printError("Cannot send chat message", e);
        }
    }

    /**
     * when the model need to call back the client to choose what effect applying
     * @param nameCard the name of the card that has different choices on the effects
     * @param choices the choices available
     * @return the number of the choice the player want
     */
    public int askChoice(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer){

        Debug.printDebug("you can choose different effect on the card " + nameCard);
        int cont = 0;
        for (String choice : choices){
            Debug.printDebug(cont + ") "+ choice);
            cont++;
        }
        Debug.printDebug(cont + ") NONE");
        Debug.printDebug("chose the effect to activate:");
        int numChoice;
        do{
            numChoice = inputScanner.nextInt();
        } while (numChoice < 0 || numChoice>choices.size() );

        return numChoice;
    }

}

