package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.effects.immediateEffects.GiveCouncilGiftEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.testingGSON.boardLoader.BoardCreator;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.utils.Debug;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by higla on 29/05/2017.
 */
public class ControllerGame {
    ControllerGame game;
    Room room;
    Board boardGame;
    public static void main(String[] args) throws Exception {
        ControllerGame controllerGame =  new ControllerGame(2);
        CliPrinter cli = new CliPrinter();
        //todo: load cards, implement these on board;
        cli.printBoard(controllerGame.getBoardGame());
    }

    public Board getBoardGame() {
        return boardGame;
    }

    /**
         *This method creates a new board and modifies it considering the number of players
         * @param numberOfPlayers is passed from room to the constructor so that boardGame is correct.
         * @throws Exception if file where Board configuration is
         */
    public ControllerGame(int numberOfPlayers, Room room) throws Exception {
        boardGame = boardCreator();
        boardGame = boardModifier(numberOfPlayers, boardGame);
        this.room = room;
    }
    public ControllerGame(int numberOfPlayers) throws Exception {
        boardGame = boardCreator();
        boardGame = boardModifier(numberOfPlayers, boardGame);
    }


    private Board boardModifier(int numberOfPlayers, Board board)
    {
        if(numberOfPlayers == 4)
            boardFourPlayers(board);
        board = boardThreePlayers(board);
        if(numberOfPlayers == 3)
            return board;
        return boardTwoPlayers(board);
    }
    private Board boardFourPlayers(Board board)
    {
        return board;
    }
    private Board boardThreePlayers(Board board)
    {
        board.getMarket().remove(3);
        board.getMarket().remove(2);
        return board;
    }
    private Board boardTwoPlayers(Board board)
    {
        board.getHarvest().setTwoPlayersOneSpace(true);

        return board;
    }
    private Board boardCreator() throws Exception
    {
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "effectName");
        runtimeTypeAdapterFactory.registerSubtype(NoEffect.class, "NoEffect");
        runtimeTypeAdapterFactory.registerSubtype(TakeOrPaySomethingEffect.class, "TakeOrPaySomethingEffect");
        runtimeTypeAdapterFactory.registerSubtype(GiveCouncilGiftEffect.class, "GiveCouncilGiftEffect");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();

        try (Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/BoardCFG.json"), "UTF-8")) {
            Board board = gson.fromJson(reader, Board.class);
           /* CliPrinter printer = new CliPrinter();
            printer.printBoard(board);*/
            return board;
        }
    }


}

