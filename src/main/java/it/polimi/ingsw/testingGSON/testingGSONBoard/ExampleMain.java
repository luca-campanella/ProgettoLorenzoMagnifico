package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gamelogic.Board.AbstractActionSpace;
import it.polimi.ingsw.gamelogic.Board.Board;
import it.polimi.ingsw.gamelogic.Board.Tower;
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
        Debug.printVerbose("Hello");
        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Tower.class, new BoardDeserializer());
        gsonBuilder.registerTypeAdapter(TowerFloorAS.class, new TowerFloorDeserializer());

        Gson gson = gsonBuilder.create();
        // The JSON data
        try(Reader reader = new InputStreamReader(ExampleMain.class.getResourceAsStream("/BoardCFG.json"), "UTF-8")) {
            // Parse JSON to Java
            // Board board = gson.fromJson(reader, Board.class);
            //board.viewBoard();

            Tower tower = gson.fromJson(reader, Tower.class);
            Debug.printVerbose("Here!");

            //printActionSpace(towerFloor);
        }
    }
    public static void printBoard(Board board){
        System.out.println("DICE: " );
    }
    public static void printActionSpace(TowerFloorAS towerFloor){
        System.out.println("DICE: " + towerFloor.getDiceValue() + " EFFECT: " + towerFloor.getEffect() );

    }
    public static void printTower(Tower tower){
        int i = 4;
        for(i= 0; i<4; i++)
            printActionSpace(tower.getFloorByIndex(i));
    }

}
