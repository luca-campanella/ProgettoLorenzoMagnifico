package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.network.AbstractConnectionPlayer;
import it.polimi.ingsw.controller.network.rmi.RMIServer;
import it.polimi.ingsw.controller.network.socket.SocketServer;
import it.polimi.ingsw.client.exceptions.LoginErrorEnum;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.ServerException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.utils.Debug;

import java.sql.SQLException;

/**
 * controller is the main class of the controller side of the application. On startup the controller creates two objects: RMIServer and SocketServer passing them the reference to himself in order to let them call himself.
 */
public class ServerMain {
	
	/**
	 * The static port used for rmi connections
	 */
	private static final int RMI_PORT = 3034;
	/**
	 * The static port used for socket connections
	 */
	private static final int SOCKET_PORT = 3035;

	/**
	 * Constant that specifies the maximum numbers of players in a game (default set to 4, set to 5 to extend functionalities)
	 */
	private static final int MAX_N_OF_PLAYERS = 4;

	/**
	 * RMIServer instance, used to create the real rmi controller that publishes an object
	 */
	RMIServer RMIServerInst;

	/**
	 * SocketServer instance, used to create the real socket controller that opens a socket and listens for connections
	 */
	SocketServer SocketServerInst;

	/**
	 * The room we are filling right now
	 */
	Room room;

	/**
	 * Private constructor to initialize the class
	 */
	private ServerMain()
	{
		Debug.instance(Debug.LEVEL_VERBOSE);

		try {
		startServer();
		} catch(ServerException e) {
			Debug.printError(e);
		}
	}

	public static void main(String[] args) {
		new ServerMain();
	}


	public static int getMaxNOfPlayers() {
		return MAX_N_OF_PLAYERS;
	}

	/**
	 * Starts the real servers
	 * @throws ServerException: in case either of the servers or both don't start
	 */
	private void startServer() throws ServerException
	{
		//creates the first Room so that it doesn't work much when the first player connects
		room = new Room(4, 3000); //TODO implement creation of room (in another class)

		try {
			DBManager.instance();
		} catch (SQLException e) {
			Debug.printError("Cannot open db or create table", e);
			throw new ServerException("Cannot open db or create table", e);
		}

		RMIServerInst = new RMIServer(this, RMI_PORT);

		SocketServerInst = new SocketServer(this, SOCKET_PORT);

	}

	/**
	 * this method is called by the AbstractServerType (either rmi or socket) to check if a player can be logged in
	 * @param nickname
	 * @param password
	 * @throws LoginException  if username doesn't exist or if password is wrong, here it would only throw NOT_EXISTING_USERNAME, WRONG_PASSWORD
	 */
	public void loginPlayer(String nickname, String password) throws LoginException
	{
		DBManager.checkLogin(nickname, password);
	}

	/**
	 * this method is called by the AbstractServerType (either rmi or socket) to register a player in the DB
	 * @param nickname to register in the controller DB
	 */
	public void registerPlayer(String nickname, String password) throws UsernameAlreadyInUseException {
		DBManager.register(nickname, password);
	}

	/**
	 * Makes the player join the first available room, if such a room is not present it creates one
	 * @param player
	 * @throws LoginException here it throws only ALREADY_LOGGED_TO_ROOM
	 */
	public void makeJoinRoomLogin(AbstractConnectionPlayer player) throws LoginException
	{
		if(room.isGameStarted()) {
			room = new Room(4, 3000);
			Debug.printDebug("Room is full, created a new one for the player " + player.getNickname());
		}
		else if(room.canJoin(player))
			//it's woth checking if the layer can join only if we haven't just created a new room. If we just created the room there is no way the player is already inside
			if(!room.canJoin(player))
				throw new LoginException(LoginErrorEnum.ALREADY_LOGGED_TO_ROOM);
		room.addNewPlayer(player);

		/*don't know if this is the best way to handle this, I don't like try catch inside try catch*/
		/*try {
			rooms.get(rooms.size() - 1).addNewPlayer(player);
		} catch (FullRoomException | GameAlreadyStartedRoomException e) {
			Debug.printDebug("Room number " + rooms.size() + " is full, create a new one for the player " + player.getNickname());
			rooms.add(new Room(4, 3000)); //TODO implement creation of room (in another class)
			try {
				rooms.get(rooms.size() - 1).addNewPlayer(player);
			} catch (FullRoomException | GameAlreadyStartedRoomException e1) {
				Debug.printError("Fatal error on the controller, exiting", e1);
				System.exit(-1);
			}
		}*/
	}

	/**
	 * Makes the player join the first available room, if such a room is not present it creates one
	 * @param player
	 */
	public void makeJoinRoomRegister(AbstractConnectionPlayer player)
	{
		if(room.isGameStarted()) {
			room = new Room(4, 10000);
			Debug.printDebug("Room is full, created a new one for the player " + player.getNickname());
		}
		room.addNewPlayer(player);

		/*don't know if this is the best way to handle this, I don't like try catch inside try catch*/
		/*try {
			rooms.get(rooms.size() - 1).addNewPlayer(player);
		} catch (FullRoomException | GameAlreadyStartedRoomException e) {
			Debug.printDebug("Room number " + rooms.size() + " is full, create a new one for the player " + player.getNickname());
			rooms.add(new Room(4, 3000)); //TODO implement creation of room (in another class)
			try {
				rooms.get(rooms.size() - 1).addNewPlayer(player);
			} catch (FullRoomException | GameAlreadyStartedRoomException e1) {
				Debug.printError("Fatal error on the controller, exiting", e1);
				System.exit(-1);
			}
		}*/
	}
}
