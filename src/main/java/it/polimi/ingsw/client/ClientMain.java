package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.exceptions.LoginException;
import it.polimi.ingsw.exceptions.NetworkException;
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
        Debug.printDebug("Sono nel ClientMain.callbackNetworkType");
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
            //istanzio socket;
        }
        userInterface.askLoginOrCreate();
    }
    public void callbackLogin(){
        Debug.printDebug("Sono nel ClientMain.callbackLogin.");
        try {
            clientNetwork.loginPlayer("TestUsrLogin", "TestPwd");
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch (LoginException e) {
            //TODO handle login problems (call the UI again)
            //Let's call a method in AbstractUIType that handles LoginFailure
            //method idea: loginFailure(String)
            //

            e.printStackTrace();

        }
    }
    public void callbackLoginAsGuest(){
        //devo settare il nome del player come Guest + ID
        userInterface.readAction();
    }
    public void callbackCreateAccount(){
        Debug.printDebug("Sono nel ClientMain.callbackCreateAccount");
        try {
            clientNetwork.registerPlayer("TestUsrRegister", "TestPwd");
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
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

}


