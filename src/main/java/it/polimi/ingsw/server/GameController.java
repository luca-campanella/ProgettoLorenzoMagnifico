package it.polimi.ingsw.server;

import it.polimi.ingsw.gamelogic.Board;

/**
 * This is the controller of one game
 */
public class GameController {
    Board gameBoard;

    GameController(int numberOfPlayers, Room room)
    {

    }

    private Board boardConfiguration(int numberOfPlayers)
    {
        BoardConfigurator boardConfigurator = new BoardConfigurator();
        gameBoard = boardConfigurator.createBoard(numberOfPlayers);
    }
}


