package it.polimi.ingsw.client.cli;

/**
 * to insert inputs on the cli
 */

import it.polimi.ingsw.client.controller.AbstractUIType;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.client.controller.ControllerCallbackInterface;
import it.polimi.ingsw.client.controller.datastructure.UsrPwdContainer;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//TODO

/**
 * this class is the CLI. It allows players to do actions, play, discard leaders.
 */
public class CommandLineUI extends AbstractUIType {

    String tmpInput;
    Scanner inputScanner = new Scanner(System.in);
    private ExecutorService pool;


    /**
     * This is the constructor of the class
     * @param controller is used to make callbacks on the controller ({@link ClientMain}
     */
    public CommandLineUI(ControllerCallbackInterface controller)
    {
        super(controller);
        pool = Executors.newFixedThreadPool(2);
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
        getController().callbackNetworkType(choice);
    }

    /**
     * This is the method which starts asking the User inputs.
     */
    public void readAction(){
        Debug.printDebug("I'm in CLI.readAction");
       /* while(true)
        {
            System.out.println("What action do you wanna make? Play a Leader, Discard a Leader, Place a family member? Write Play, Discard, Place");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("Play")){
                getController().callbackPlayLeader();
                break;
            }
            if(tmpInput.equalsIgnoreCase("Discard")){
                getController().callbackDiscardLeader();
                break;
            }
            if(tmpInput.equalsIgnoreCase("Place")){
                getController().callbackPerformPlacement();
                break;
            }

        }*/
    }

    /**
     * this method allows the user to select a familyMember
     */
    public void selectFamilyMember()
    {
        /*int i;
        String familyColorID;
        System.out.print("Select a family member. You can choose " );

        System.out.print("Yellow, Red, Green, Neutral");
        System.out.println();
        while(true) {
            familyColorID = inputScanner.nextLine();
            if(existingColors(familyColorID))
            break;
        }
        getController().callbackFamilyMemberSelected(familyColorID);*/
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
                getController().callbackCreateAccount(usrAndPwd.getNickname(), usrAndPwd.getPassword());
                break;
            }

            if(tmpInput.equalsIgnoreCase("LogIn")){
                usrAndPwd = readUsrPwd();
                getController().callbackLogin(usrAndPwd.getNickname(), usrAndPwd.getPassword());
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

    /**
     * this method allows CLI to ask proper Database's info to user.
     * @return
     */
    private UsrPwdContainer readUsrPwd()
    {
        String nickname, password;

        System.out.println("Insert UserId");
        nickname = inputScanner.nextLine();
        System.out.println("Insert PassWord");
        password = inputScanner.nextLine();

        return new UsrPwdContainer(nickname, password);
    }

    /*
    todo: we need to cancel this
     */
    public void createNewAccount(){
        System.out.println("Creating new Account...");
    }
    /*
    todo: we need to cancel this one too
     */
    public void askLogin(){
        System.out.println("Logging In...");
    }

    /**
     * todo: what does this method really do?
     * Chiama il criprinter, il quale è singleton e in base a non so quali parametri chiama il metodo giusto?
     * A questo punto: meglio far chiamare direttamente dal controller un GenericPrinter o dalla CLI il CLIPrinter.
     */
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

    /**
     * if an error occurs, this method printsit
     * @param error
     */
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
            getController().callbackSendChatMsg(inputScanner.nextLine());
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

    /**
     * Used when it's the turn of the user and he has to choose which action he wants to perform
     * This method will trigger either
     * {@link ControllerCallbackInterface#callbackFamilyMemberAndServantsSelected(it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum, int)} or
     * //todo other methods triggered
     */
    @Override
    public void askInitialAction() {
        InitialActionMenu menu = new InitialActionMenu(getController());

        pool.submit(menu);
    }

    /**
     * This method is called when a choice on a council gift should be perfomed by the ui
     *
     * @param options
     * @returns to controller the index of the selected option, the choice the user made
     */
    @Override
    public int askCouncilGift(ArrayList<GainResourceEffect> options) {
        CliOptionsHandler optionsHandler = new CliOptionsHandler(options.size());
        optionsHandler.addEffectsArrayList(options);
        return optionsHandler.askUserChoice();
    }

    /**
     * This method is called when a choice on which effect to activate in a yellow card should be perfomed by the ui
     * @param possibleEffectChoices
     * @return the index of the chosen effect
     */
    @Override
    public int askYellowBuildingCardEffectChoice(ArrayList<ImmediateEffectInterface> possibleEffectChoices) {
        CliOptionsHandler optionsHandler = new CliOptionsHandler(possibleEffectChoices.size());
        optionsHandler.addEffectsArrayList(possibleEffectChoices);
        optionsHandler.addOption("Do not activate any effect and save resources for later");
        int choice = optionsHandler.askUserChoice();
        if(choice == possibleEffectChoices.size())
            return -1; // the player chose not to activate any effect
        return choice;
    }

    /**
     * This method is called when a choice on which cost to pay in a purple card should be perfomed by the ui
     * @param costChoiceResource the list of resources the player will pay if he chooses this option
     * @param costChoiceMilitary the cost he will pay on something conditioned
     * @return 0 if he chooses to pay with resources, 1 with military points
     */
    @Override
    public int askPurpleVentureCardCostChoice(List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
        CliOptionsHandler optionsHandler = new CliOptionsHandler(2);
        StringBuilder optionResDescr = new StringBuilder();

        optionResDescr.append("pay the card with this resources: ");

        for(Resource resIter : costChoiceResource) {
            optionResDescr.append(resIter.getResourceFullDescript() + " | ");
        }

        optionsHandler.addOption(optionResDescr.toString());
        optionsHandler.addOption("Pay " + costChoiceMilitary.getResourceCost().getResourceFullDescript() + "(you fulfill the requirement of " + costChoiceMilitary.getResourceRequirement().getResourceFullDescript());
        int choice = optionsHandler.askUserChoice();
        return choice;
    }

}

