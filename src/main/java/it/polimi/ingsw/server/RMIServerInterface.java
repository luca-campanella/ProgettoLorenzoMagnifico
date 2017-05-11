package it.polimi.ingsw.server;

import java.rmi.*;
import client.RMICPlayerInterface;

/**
 * This interface lets the client call functions on the server via RMI
 */
public interface RMIServerInterface extends Remote {
	
	public String loginPlayer(String nickname, RMIClientInterface RMIClientInterfaceInst);
	
}
