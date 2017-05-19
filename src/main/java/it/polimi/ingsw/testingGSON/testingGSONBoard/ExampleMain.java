package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gamelogic.Board.AbstractActionSpace;
import it.polimi.ingsw.gamelogic.Board.Board;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;
import it.polimi.ingsw.utils.Debug;

import java.io.InputStreamReader;
import java.io.Reader;

/**
* Created by higla on 17/05/2017.
*/
public class ExampleMain {

//qui chiamo il BoardDeserializer
//stampo anche in console la board per vedere se è passata correttamente
    public static void main(String[] args) throws Exception {

        Debug.instance(Debug.LEVEL_VERBOSE);

        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BoardDeserializer.class, new BoardDeserializer());
        //we have to add all Deserializers
        Gson gson = gsonBuilder.create();
        // The JSON data
        try(Reader reader = new InputStreamReader(ExampleMain.class.getResourceAsStream("/BoardCFG.json"), "UTF-8")) {
            // Parse JSON to Java
            // Board board = gson.fromJson(reader, Board.class);
            //board.viewBoard();
            TowerFloorAS actionSpace = gson.fromJson(reader, TowerFloorAS.class);
            //printActionSpace(actionSpace);
        }
    }
    public static void printBoard(Board board){
        System.out.println("DICE: " );
    }
    public static void printActionSpace(TowerFloorAS actionSpace){
        System.out.println("DICE: " + actionSpace.getDiceValue() + "Effect" + actionSpace.getEffect() );

    }

}
