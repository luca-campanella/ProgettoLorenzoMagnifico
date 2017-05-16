package it.polimi.ingsw.server;

import it.polimi.ingsw.gamelogic.Player.Player;

/**
 * The abstract class that extends Player and handles connections either via socker or via RMI
 */
public abstract class AbstractConnectionPlayer extends Player {

    public AbstractConnectionPlayer(String nickname)
    {
        super(nickname);
    }
}
