package it.polimi.ingsw.client.exceptions;


/**
 * The enum to signal what caused the login error
 */
public enum LoginErrorEnum {
    NOT_EXISTING_USERNAME,
    WRONG_PASSWORD,
    /**
     * The same player can play multiple games, but not in the same room
     */
    ALREADY_LOGGED_TO_ROOM
}
