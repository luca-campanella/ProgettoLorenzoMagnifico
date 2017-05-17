package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gamelogic.Board.AbstractActionSpace;
import it.polimi.ingsw.gamelogic.Board.Board;
import java.io.InputStreamReader;
import java.io.Reader;

/**
* Created by higla on 17/05/2017.
*/
public class ExampleMain {

//qui chiamo il BoardDeserializer
//stampo anche in console la board per vedere se è passata correttamente
    public static void main(String[] args) throws Exception {
        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AbstractActionSpace.class, new ActionSpaceDeserializer());
        //we have to add all Deserializers
        Gson gson = gsonBuilder.create();

        // The JSON data
        try(Reader reader = new InputStreamReader(it.polimi.ingsw.testingGSON.GsonExample.class.getResourceAsStream("/BoardCFG.json"), "UTF-8")) {
            // Parse JSON to Java
            // Board board = gson.fromJson(reader, Board.class);
            //board.viewBoard();
            AbstractActionSpace actionSpace = gson.fromJson(reader, AbstractActionSpace.class);
        printActionSpace(actionSpace);
        }
    }
    public static void printActionSpace(AbstractActionSpace actionSpace){
        System.out.println("DICE: " + actionSpace.getDICEVALUE() + "EFFECT: " + actionSpace.getEFFECT() );
    }
}
