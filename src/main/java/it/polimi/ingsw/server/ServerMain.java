package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.utils.Debug;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * server is the main class of the server side of the application. On startup the server creates two objects: RMIServer and SocketServer passing them the reference to himself in order to let them call himself.
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
	 * RMIServer instance, used to create the real RMI server that publishes an object
	 */
	RMIServer RMIServerInst;

	/**
	 * SocketServer instance, used to create the real Socket server that opens a socket and listens for connections
	 */
	SocketServer SocketServerInst;

	/**
	 * The list of the created rooms
	 */
	ArrayList<Room> rooms;

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
		//creates the first Room so that it doesn't work much when the first player connects and plus we have an object in the array, which comes useful afterwards
		rooms = new ArrayList<Room>(1);
		rooms.add(new Room(4, 3000)); //TODO implement creation of room (in another class)

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
	 * this method is called by the AbstractServerType (either RMI or Socket) to check if a player can be logged in
	 * @param nickname
	 * @param password
	 * @throws LoginException  if username doesn't exist or if password is wrong, here it would only throw NOT_EXISTING_USERNAME, WRONG_PASSWORD
	 */
	public void loginPlayer(String nickname, String password) throws LoginException
	{
		DBManager.checkLogin(nickname, password);
	}

	/**
	 * this method is called by the AbstractServerType (either RMI or Socket) to register a player in the DB
	 * @param nickname to register in the server DB
	 */
	public void registerPlayer(String nickname, String password) throws UsernameAlreadyInUseException {
		DBManager.register(nickname, password);
	}

	/**
	 * Makes the player join the first available room, if such a room is not present it creates one
	 * @param player
	 * @throws LoginException here it throws only ALREADY_LOGGED_TO_ROOM
	 */
	public void makeJoinRoom(AbstractConnectionPlayer player) throws LoginException
	{
		/*don't know if this is the best way to handle this, I don't like try catch inside try catch*/
		try {
			rooms.get(rooms.size() - 1).addNewPlayer(player);
		} catch (FullRoomException | GameAlreadyStartedRoomException e) {
			Debug.printDebug("Room number " + rooms.size() + " is full, create a new one for the player " + player.getNickname());
			rooms.add(new Room(4, 3000)); //TODO implement creation of room (in another class)
			try {
				rooms.get(rooms.size() - 1).addNewPlayer(player);
			} catch (FullRoomException | GameAlreadyStartedRoomException e1) {
				Debug.printError("Fatal error on the server, exiting", e1);
				System.exit(-1);
			}
		}
	}
}
