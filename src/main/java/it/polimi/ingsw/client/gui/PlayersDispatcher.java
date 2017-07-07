package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * This class helps identifying if a leaderOwned scene has already been created.
 *
 */
public class PlayersDispatcher {
    //this is a list of all players. This list won't change order
    List<Player> players;
    //this array checks if a scene has already been created.
    boolean[] hasSceneAlreadyBeenCreated = {false,false,false,false,false};
}
