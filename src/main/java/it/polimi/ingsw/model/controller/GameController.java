package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.controller.BoardConfigurator;
import it.polimi.ingsw.controller.Room;

import java.util.LinkedList;

/**
 * This is the controller of one game
 */
public class GameController {

    /**
     * the room that this controller manages
     */
    private Room room;

    /**
     * the board of this game
     */
    private Board gameBoard;

    /**
     * the players that play in this game
     */
    private LinkedList<Player> players;

    public GameController(int numberOfPlayers, Room room)
    {

    }

    private Board boardConfiguration(int numberOfPlayers)
    {
        BoardConfigurator boardConfigurator = new BoardConfigurator();
        gameBoard = boardConfigurator.createBoard(numberOfPlayers);
        return gameBoard;
    }
}


