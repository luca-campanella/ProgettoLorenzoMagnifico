package it.polimi.ingsw.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import it.polimi.ingsw.client.RMIClientInterface;
import it.polimi.ingsw.exceptions.LoginException;

/**
 * This interface lets the client call functions on the server via RMI
 */
public interface RMIServerInterface extends Remote {

	/**
	 * this method is used when an user already exists and decides to login with his username and password, real implementation of the abstract method
	 *
	 * @param nickname
	 * @param password
	 * @throws RemoteException
	 * @throws LoginException   if username doesn't exist or if password is wrong
	 */
	public String loginPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException, LoginException;

	/**
	 * this method is used when the user has never played and wants to create an account
	 * @param nickname to register in the server DB
	 * @param password to register in the server DB
	 * @return
	 * @throws RemoteException if something goes wrong during the connection
	 */
	public String registerPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException;

}
