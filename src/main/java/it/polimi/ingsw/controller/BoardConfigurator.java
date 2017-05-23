package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Board;

/**
 * This class build cards from a JSON File
 */
public class BoardConfigurator {
    //La classe deve scorrere un file JSON
    //Per ogni riga del file JSON instanzio una nuova carta.
    //Se non va abbastanza bene carico una configuration di default. Per andare bene non devo avere errori
    public Board createBoard(int numberOfPlayers)
    {
        Board board = new Board();
    return board;
    }
}
