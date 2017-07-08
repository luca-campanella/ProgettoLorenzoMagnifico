package it.polimi.ingsw.server;

import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;

/**
 * .
 */
public class JSONLoaderTester {
    public static void main(String [ ] args)
    {
        JSONLoader.instance();
        Board board = null;
        Debug.instance(3);
        try {
             board = JSONLoader.boardCreator();
        }
        catch (IOException io)
        {
            Debug.printVerbose("Entro qua");
        }

    }
}
