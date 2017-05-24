package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.network.socket.protocol.FunctionResponse;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;
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

    private int round;

    private int period;

    public GameController(int numberOfPlayers, Room room)
    {

        this.room = room;
        initializeGame = new HashMap<>(5);
        dices = new ArrayList<>(3);
        loadDices();
        loadInfoInitialization();
        doMethod(numberOfPlayers);
        round=0;
        period=0;
        prepareForNewRound();

    }

    private void loadDices(){

        dices.add(new Dice(DiceAndFamilyMemberColor.BLACK));
        dices.add(new Dice(DiceAndFamilyMemberColor.NEUTRAL));
        dices.add(new Dice(DiceAndFamilyMemberColor.ORANGE));
        dices.add(new Dice(DiceAndFamilyMemberColor.WHITE));

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

    private void prepareForNewRound(){

        players.forEach(Player::resetFamilyMember);

        //TODO clean and load the cards on board
        //TODO change order players

        if(round%2==0)
            //TODO METHOD to call the excommunication
        round = round + 1;
    }

    public void prepareForNewPeriod(){

        period = period + 1;
    }

    private void boardConfiguration(int numberOfPlayers)
    {
        BoardConfigurator boardConfigurator = new BoardConfigurator();
        gameBoard = boardConfigurator.createBoard(numberOfPlayers);
    }
}


