package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.utils.Debug;

/**
 * TEST
 */
public class ClientMain {
    LauncherClientFake temp;
    AbstractUIType userInterface;
    AbstractClientType clientNetwork;

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

    public AbstractUIType getUserInterface() {
        return userInterface;
    }

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
        //chiamo il server e gli dico che voglio usare quel family member.
        //il server mi dice quali azioni posso fare
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

    public void callbackSendChatMsg(String msg) throws NetworkException {
        clientNetwork.sendChatMsg(msg);
    }
}


