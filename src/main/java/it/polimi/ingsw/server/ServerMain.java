package it.polimi.ingsw.server;

import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.server.network.rmi.RMIServer;
import it.polimi.ingsw.server.network.socket.SocketServer;
import it.polimi.ingsw.client.exceptions.LoginErrorEnum;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.ServerException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.sql.SQLException;

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
	 * RMIServer instance, used to create the real rmi server that publishes an object
	 */
	RMIServer RMIServerInst;

	/**
	 * SocketServer instance, used to create the real socket server that opens a socket and listens for connections
	 */
	SocketServer SocketServerInst;

	/**
	 * The room we are filling right now
	 */
	Room room;

	/**
	 * This is the configuations for timeout in room, it is loaded from json at serves startup
	 */
	RoomConfigurator roomConfigurator;

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
			System.out.println("Error in starting the server, please restart. Error message: " + e.getMessage());
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
	 * @throws ServerException: in case either of the servers or both don't start or something else goes wrong
	 */
	private void startServer() throws ServerException
	{
		JSONLoader.instance();
		try {
			roomConfigurator = JSONLoader.loadTimeoutInSec();
			//creates the first Room so that it doesn't work much when the first player connects
			room = new Room(4, roomConfigurator.getTimeoutSec(), roomConfigurator.getTimeToPass());
		}
		catch(IOException e)
		{
			Debug.printError("Json not loaded properly. Restart server");
			throw new ServerException("Cannot load from json", e);
		}

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
	 * @param nickname it is the nick of the player
	 * @param password it is the pw of the player
	 * @throws LoginException  if username doesn't exist or if password is wrong, here it would only throw NOT_EXISTING_USERNAME, WRONG_PASSWORD
	 */
	public void loginPlayer(String nickname, String password) throws LoginException
	{
		DBManager.checkLogin(nickname, password);
	}

	/**
	 * this method is called by the AbstractServerType (either rmi or socket) to register a player in the DB
	 * @param nickname to register in the server DB
	 */
	public void registerPlayer(String nickname, String password) throws UsernameAlreadyInUseException {
		DBManager.register(nickname, password);
	}

	/**
	 * Makes the player join the first available room, if such a room is not present it creates one
	 * @param player it's the class of the player
	 * @throws LoginException here it throws only ALREADY_LOGGED_TO_ROOM
	 */
	public void makeJoinRoomLogin(AbstractConnectionPlayer player) throws LoginException
	{
		if(room.isGameStarted()) {
				room = new Room(4, roomConfigurator.getTimeoutSec(), roomConfigurator.getTimeToPass());
				Debug.printDebug("Room is full, created a new one for the player " + player.getNickname());
		}
		else if(!room.canJoin(player))//it's worth checking if the layer can join only if we haven't just created a new room. If we just created the room there is no way the player is already inside
				throw new LoginException(LoginErrorEnum.ALREADY_LOGGED_TO_ROOM);
		room.addNewPlayer(player);
	}

	/**
	 * Makes the player join the first available room, if such a room is not present it creates one
	 * @param player
	 */
	public void makeJoinRoomRegister(AbstractConnectionPlayer player)
	{
		if(room.isGameStarted()) {
			room = new Room(4, roomConfigurator.getTimeoutSec(), roomConfigurator.getTimeToPass());
			Debug.printDebug("Room is full, created a new one for the player " + player.getNickname());
		}

		room.addNewPlayer(player);
	}
}
