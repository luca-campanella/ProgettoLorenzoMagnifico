package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.ClientConnectionException;

/**
 * TEST
 */
public class ClientMain {

    private ClientMain()
    {
        AbstractClientType clientNetwork = new RMIClient(this, "127.0.0.1", 3034);
        try {
            clientNetwork.connect();
        } catch (ClientConnectionException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        new ClientMain();
    }
}
