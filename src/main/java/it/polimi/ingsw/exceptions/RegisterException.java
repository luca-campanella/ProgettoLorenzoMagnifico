package it.polimi.ingsw.exceptions;

import java.io.IOException;

/**
 * exception throwed when there are problems with the player's registration
 */
public class RegisterException extends IOException {
public RegisterException(){
    super("Nickname already used");
}
}
