package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.exceptions.ClientConnectionException;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.client.network.rmi.RMIClient;
import it.polimi.ingsw.client.network.socket.SocketClient;
import it.polimi.ingsw.model.controller.ModelController;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * TODO: implement launcher
 */
public class ClientMain implements ControllerModelInterface{
    LauncherClientFake temp;
    AbstractUIType userInterface;
    AbstractClientType clientNetwork;
    ModelController modelController;

    /**
    this is Class Constructor
     */
    private ClientMain()
    {
        temp = new LauncherClientFake(this);
        userInterface = temp.welcome();
        userInterface.askNetworkType();
        //Questo non penso vada bene in quanto credo debba essere il metodo corrispondente ad istanziare la classe corrispondente --Arto

    }
    public static void main(String args[]) {
        Debug.instance(Debug.LEVEL_VERBOSE);

        new ClientMain();
    }
    /*
    This method returns Client's userInterface
     */
    public AbstractUIType getUserInterface() {
        return userInterface;
    }
    /**
    * This method show user's network choice
     */
    public void callbackNetworkType(NetworkTypeEnum networkChoice){
        Debug.printDebug("I'm in ClientMain.callbackNetworkType, choice = " + networkChoice);
        if(networkChoice == NetworkTypeEnum.RMI) {
            clientNetwork = new RMIClient(this, "127.0.0.1", 3034);
            try {
                clientNetwork.connect();
            } catch (ClientConnectionException e) {
                e.printStackTrace();
                //TODO: handling no Connection
            }
        }
        else {//Here enters if network type is a socket
            clientNetwork = new SocketClient(this, "127.0.0.1", 3035);
            try {
                clientNetwork.connect();
            } catch (ClientConnectionException e) {
                e.printStackTrace();
                //TODO: handling no Connection
            }
        }
        userInterface.askLoginOrCreate();
    }
    /**
    * this method is called when a user is trying to login.
     */
    public void callbackLogin(String userID, String userPW){
        Debug.printDebug("Sono nel ClientMain.callbackLogin.");
        try {
            clientNetwork.loginPlayer(userID, userPW);
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch (LoginException e) {
            //TODO handle login problems (call the UI again)
            Debug.printDebug("Login exception occurred", e);
            switch(e.getErrorType()) {
                case ALREADY_LOGGED_TO_ROOM :
                    userInterface.printError("Already logged to room");
                    break;
                case NOT_EXISTING_USERNAME:
                    userInterface.printError("The username you inserted doesn't exists");
                    userInterface.askLoginOrCreate();
                break;
                case WRONG_PASSWORD:
                    userInterface.printError("The password you inserted was wrong");
                    userInterface.askLoginOrCreate();
                    break;
                default:
                    userInterface.printError("Something went wrong.");
                    userInterface.askLoginOrCreate();
                    break;

            }

        }
        Debug.printVerbose("Im going to call askChatMsg");
        userInterface.askChatMsg(); //TODO this is a method just for testing chat
    }
    public void callbackLoginAsGuest(){
        //devo settare il nome del player come Guest + ID
        userInterface.readAction();
    }
    public void callbackCreateAccount(String userID, String userPW){
        Debug.printDebug("I'm in ClientMain.callbackCreateAccount");
        try {
            clientNetwork.registerPlayer(userID, userPW);
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch(UsernameAlreadyInUseException e)
        {
            Debug.printDebug(e);
            userInterface.printError("The username you inserted is already in use, please insert a new one");
            userInterface.askLoginOrCreate();
        }
        userInterface.askLoginOrCreate();
    }

    /**
     * this method it's a callback method that is called from the AbstractyUIType when i want to play a Leader
     */
    public void callbackPlayLeader(){
        Debug.printDebug("I'm in ClientMain.callbackPlayLeader");
    }

    /**
     * this method it's a callback method called from AbstractUIType when i want to discard a Leader.
     */
    public void callbackDiscardLeader(){
        Debug.printDebug("I'm in ClientMain.callbackDiscardLeader");
    }

    /**
     * this method is a callback method called from abstractUIType when a placement of a family member is performed
     */
    public void callbackPerformPlacement(){
        Debug.printDebug("I'm in ClientMain.callbackPerformPlacement");
        //get status... ricevo una lista di family member che posso usare
        userInterface.selectFamilyMember();
    }

    /**
     * this method is a callback method called from abstractUiType when a family member is selected
     * @param color refers to the color of the family member selected.
     */
    public void callbackFamilyMemberSelected(String color)
    {
        Debug.printDebug("Sono nel ClientMain.callbackFamilyMember");
        //chiamo il server e gli dico che voglio usare quel family member.
        //il server mi dice quali azioni posso fare
        //chiamerò quindi il mio abstract UIType con un qualcosa riguardante...
        userInterface.printAllowedActions();
    }

    /**
     * this method will ask to the model Controller what action user can do
     */
    public void askAction(){
        userInterface.readAction();
    }

    /**
     * This method is called by AbstractClientType to display an incoming chat message (Direction: AbstractClientType -> ClientMain; general direction: Server -> Client)
     * @param senderNick
     * @param msg
     */
    public void receiveChatMsg(String senderNick, String msg) {
        userInterface.displayChatMsg(senderNick, msg);
    }


    /**
     * this is the call back method to send a message to all other players in the room (Direction: {@link AbstractUIType} -> {@link ClientMain}; general direction: Client -> server)
     * @param msg
     * @throws NetworkException
     */
    public void callbackSendChatMsg(String msg) throws NetworkException {
        clientNetwork.sendChatMsg(msg);
    }

    /**
     * Questo metodo che fa? Non c'è nessuno che lo sa -- Arto todo: comment this method
     * @param nameCard
     * @param choices
     * @param resourcePlayer
     * @return
     */
    public int choose(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer){

        return userInterface.askChoice(nameCard, choices, resourcePlayer);

    }

    /**
     * this method allows player to place a family member on a build action space
     * @param familyMember
     * @param servants
     */
    public void callbackPlacedFMOnBuild(FamilyMember familyMember, Resource servants){
        /*We make a copy of the hashmap beacuse we ahve to perfoms some checks on it and this checks should not affect
        the hashmap of the player. Even tough making a copy using the constructor makes just a shallow copy, this is sufficient
        since Integer types are immutable
         */

        HashMap<ResourceTypeEnum, Integer> controlHashMap = new HashMap<ResourceTypeEnum, Integer>(familyMember.getPlayer().getResourcesMap());
        LinkedList<ChoiceContainer> choices;

        choices = modelController.getChoicesOnBuild(familyMember, servants);
    }
}


