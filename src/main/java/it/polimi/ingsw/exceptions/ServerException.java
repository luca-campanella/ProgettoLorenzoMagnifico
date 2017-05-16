package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * This class is used to signal that something went wrong with the sever
 * @extends IOException
 */
public class ServerException extends IOException {
	
	/**
	 * Just class the superclass method 
	 * @param msg the desired exception message
	 */
	public ServerException(String msg) {
		super(msg);
	}

	/**
	 * Just class the superclass method
	 * @param msg the desired exception message
	 * @param e the exception that generated the problem
	 */
	public ServerException(String msg, Exception e) {
		super(msg, e);
	}

}
