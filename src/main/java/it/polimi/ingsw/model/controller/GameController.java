package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.network.socket.protocol.FunctionResponse;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.controller.BoardConfigurator;
import it.polimi.ingsw.controller.Room;

import java.util.ArrayList;
import java.util.HashMap;
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

    private ArrayList<Dice> dices;

    private HashMap<Integer, FunctionResponse> initializeGame;

    public GameController(int numberOfPlayers, Room room)
    {

        this.room = room;
        initializeGame = new HashMap<>(5);
        loadInfoInitialization();
        doMethod(numberOfPlayers);

    }

    private void loadInfoInitialization(){

        initializeGame.put(2, this::gameFor2);
        initializeGame.put(3, this::gameFor3);
        initializeGame.put(4, this::gameFor4);
        initializeGame.put(5, this::gameFor5);

    }

    public void doMethod(int numberOfPlayers){

        FunctionResponse initialization=initializeGame.get(numberOfPlayers);
        initialization.chooseMethod();

    }

    private void gameFor5(){

        //TODO method
        gameFor4();

    }

    private void gameFor4(){

        //TODO method
        gameFor3();

    }

    private void gameFor3(){

        //TODO method
        gameFor2();

    }

    private void gameFor2(){

        //TODO method

    }

    private Board boardConfiguration(int numberOfPlayers)
    {
        BoardConfigurator boardConfigurator = new BoardConfigurator();
        gameBoard = boardConfigurator.createBoard(numberOfPlayers);
        return gameBoard;
    }
}


