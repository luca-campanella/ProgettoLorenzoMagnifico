package it.polimi.ingsw.server;

/**
 * This class is the Player via RMI
 */
public class RMIPlayer extends AbstractConnectionPlayer implements RMIPlayerInterface {

    public RMIPlayer(String nickname)
    {
        super(nickname);
    }
}
