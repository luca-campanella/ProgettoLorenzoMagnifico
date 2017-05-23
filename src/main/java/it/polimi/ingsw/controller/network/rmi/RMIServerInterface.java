package it.polimi.ingsw.controller.network.rmi;

import it.polimi.ingsw.client.network.rmi.RMIClientInterface;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface lets the client call functions on the controller via rmi
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
	public RMIPlayerInterface loginPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException, LoginException;

	/**
	 * this method is used when the user has never played and wants to create an account
	 * @param nickname to register in the controller DB
	 * @param password to register in the controller DB
	 * @return
	 * @throws RemoteException if something goes wrong during the connection
	 */
	public RMIPlayerInterface registerPlayer(String nickname, String password, RMIClientInterface RMIClientInterfaceInst) throws RemoteException, UsernameAlreadyInUseException;

}
