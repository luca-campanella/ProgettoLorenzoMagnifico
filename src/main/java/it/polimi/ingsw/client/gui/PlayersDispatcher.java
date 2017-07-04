package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * This class helps identifying if a leaderOwned scene has already been created.
 * //todo: this solution has a problem? Se io già carico una scena, poi sugli altri pc come funziona?
 * //todo: nel senso.. fino a quando io sono su una macchina posso avere anche 25 finestre aperte
 * //todo: (5 per ogni giocatore). Su più macchine saranno alla fine  max 5...
 *
 */
public class PlayersDispatcher {
    //this is a list of all players. This list won't change order
    List<Player> players;
    //this array checks if a scene has already been created.
    boolean[] hasSceneAlreadyBeenCreated = {false,false,false,false,false};
}
