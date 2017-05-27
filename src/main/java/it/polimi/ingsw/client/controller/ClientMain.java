package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.client.network.rmi.RMIClient;
import it.polimi.ingsw.client.network.socket.SocketClient;
import it.polimi.ingsw.client.exceptions.ClientConnectionException;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.utils.Debug;

/**
 * TODO: implement launcher
 */
public class ClientMain {
    LauncherClientFake temp;
    AbstractUIType userInterface;
    AbstractClientType clientNetwork;
    /*
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
    /*
    This method show user's network choice
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
    /*
    this method is called when a user is trying to login.
     */
    public void callbackLogin(String userID, String userPW){
        Debug.printDebug("Sono nel ClientMain.callbackLogin.");
        try {
            clientNetwork.loginPlayer(userID, userPW);
            Debug.printDebug("Sono dopo clientNetwork.");

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
        Debug.printVerbose("Sto per chiamare askChatMsg");
        Debug.printDebug("hellooo");
        userInterface.askChatMsg(); //TODO this is a method just for testing chat
    }
    public void callbackLoginAsGuest(){
        //devo settare il nome del player come Guest + ID
        userInterface.readAction();
    }
    public void callbackCreateAccount(String userID, String userPW){
        Debug.printDebug("Sono nel ClientMain.callbackCreateAccount");
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
    }
    public void callbackPlayLeader(){
        Debug.printDebug("Sono nel ClientMain.callbackPlayLeader");
    }
    public void callbackDiscardLeader(){
        Debug.printDebug("Sono nel ClientMain.callbackDiscardLeader");
    }
    public void callbackPerformPlacement(){
        Debug.printDebug("Sono nel ClientMain.callbackPerformPlacement");
        //get status... ricevo una lista di family member che posso usare
        userInterface.selectFamilyMember();
    }
    public void callbackFamilyMemberSelected(String color)
    {
        Debug.printDebug("Sono nel ClientMain.callbackFamilyMember");
        //chiamo il controller e gli dico che voglio usare quel family member.
        //il controller mi dice quali azioni posso fare
        //chiamerò quindi il mio abstract UIType con un qualcosa riguardante...
        userInterface.printAllowedActions();
    }
    //faccio un oggetto di azioni permesse
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
     * this is the call back method to send a message to all other players in the room (Direction: {@link AbstractUIType} -> {@link ClientMain}; general direction: Client -> controller)
     * @param msg
     * @throws NetworkException
     */
    public void callbackSendChatMsg(String msg) throws NetworkException {
        clientNetwork.sendChatMsg(msg);
    }
}


