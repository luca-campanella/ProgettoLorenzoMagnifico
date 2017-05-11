package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.ClientConnectionException;
import it.polimi.ingsw.server.RMIServerInterface;
import it.polimi.ingsw.utils.Debug;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

/**
 * The class to handle the RMI client side, publishes itself and passes its reference to the server, so the server can call it back with RMIClientInterface
 */
public class RMIClient extends AbstractClientType implements RMIClientInterface {

    private Registry registry;
    private RMIServerInterface RMIServerInterfaceInst;

    public RMIClient(ClientMain controllerMain, String serverAddress, int port) {
        super(controllerMain, serverAddress, port);
        Debug.printDebug("Creator of RMIClient called");
    }

    @Override
    public void connect() throws ClientConnectionException {
        try {
            registry = LocateRegistry.getRegistry(serverAddress, port);
            Debug.printDebug("Got Registry");
            RMIServerInterfaceInst = (RMIServerInterface) registry.lookup("RMIServerInterface");
            Debug.printDebug("Got Object");
            UnicastRemoteObject.exportObject(this, 0); //with 0 exports the object on a random port
            Debug.printDebug("RMI Client connected succesfully");

        } catch(RemoteException | NotBoundException e) {
            Debug.printError("Cannot connect RMI client", e);
            throw new ClientConnectionException(e);
        }

    }
}
