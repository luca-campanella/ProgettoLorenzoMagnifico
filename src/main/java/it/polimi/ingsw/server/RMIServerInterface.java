package it.polimi.ingsw.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import it.polimi.ingsw.client.RMIClientInterface;
import it.polimi.ingsw.exceptions.LoginException;

/**
 * This interface lets the client call functions on the server via RMI
 */
public interface RMIServerInterface extends Remote {

	public String loginPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException, LoginException;
	public String registerPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException;

}
