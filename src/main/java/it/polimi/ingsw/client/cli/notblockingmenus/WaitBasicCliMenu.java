package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.player.Player;

/**
 * this is the view showe when is not the turn of the player
 */
public class WaitBasicCliMenu extends BasicCLIMenu {

    public WaitBasicCliMenu(ViewControllerCallbackInterface controller){

        super("decide what to do while waiting the other players", controller);
        loadOptions();
    }

    public WaitBasicCliMenu(String initialMessage,  ViewControllerCallbackInterface controller){

        super(initialMessage, controller);
        loadOptions();
    }

    private void loadOptions(){

        addOption("BOARD", "Show me the board", this::printBoard);
        addOption("PB", "Show the personal board of the player", this::printPersonalBoard);
        addOption("PBO", "Show the personal board of the other players", this::printPersonalBoardOtherPlayers);

    }

    /**
     * this method is called to show the board of the game
     */
    public void printBoard(){

        getController().printBoard();
        showMenuAndAsk();

    }

    /**
     * this method is called to show the personal board of the player
     */
    public void printPersonalBoard(){

        getController().printPersonalBoard();
        showMenuAndAsk();

    }

    /**
     * this method is called to show the personal board of the other players
     */
    public void printPersonalBoardOtherPlayers(){

        getController().printPersonalBoardOtherPlayers();
        showMenuAndAsk();

    }

}