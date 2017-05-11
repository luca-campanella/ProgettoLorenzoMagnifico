package it.polimi.ingsw.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import it.polimi.ingsw.client.RMIClientInterface;

/**
 * This interface lets the client call functions on the server via RMI
 */
public interface RMIServerInterface extends Remote {

	public String loginPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException;
	public String registerPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException;

}
