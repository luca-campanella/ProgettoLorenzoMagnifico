package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.utils.Debug;
import it.polimi.ingsw.exceptions.ClientConnectionException;
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
        Debug.instance(Debug.LEVEL_NORMAL);
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
            }
        }
        else {//Here enters if network type is a socket
            //istanzio socket;
        }
        userInterface.askLoginOrCreate();
    }
    public void callbackLogin(){
        Debug.printDebug("Sono nel ClientMain.callbackLogin.");
    }
    public void callbackCreateAccount(){
        Debug.printDebug("Sono nel ClientMain.callbackCreateAccount");
    }
    public void callbackPlayLeader(){
        Debug.printDebug("Sono nel ClientMain.callbackPlayLeader");
    }
    public void callbackDiscardLeader(){
        Debug.printDebug("Sono nel ClientMain.callbackDiscardLeader");
    }
    public void callbackPerformPlacement(){
        Debug.printDebug("Sono nel ClientMain.callbackPerformPlacement");
    }
    public void askAction(){
        userInterface.readAction();
    }

}


