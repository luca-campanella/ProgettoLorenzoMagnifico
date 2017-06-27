package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.utils.Debug;

/**
 * this is the view showed when is not the turn of the player
 * This object overrides run method form BasiCLIMenu so that its thread can be interrupted at any time
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
     * Asks the user an imput
     * Override of method in {@link BasicCLIMenu} so that this thread can be interrupted at any time
     */
    @Override
    public void run() {
        Debug.printVerbose("Process Started");

        showMenuAndAskNonBlocking();
    }

    /**
     * this method is called to show the board of the game
     */
    public void printBoard(){

        CliPrinter.printBoard(getController().callbackObtainBoard());
        showMenuAndAskNonBlocking();

    }

    /**
     * this method is called to show the personal board of the player
     */
    public void printPersonalBoard(){

        CliPrinter.printPersonalBoard(getController().callbackObtainPlayer());
        showMenuAndAskNonBlocking();

    }

    /**
     * this method is called to show the personal board of the other players
     */
    public void printPersonalBoardOtherPlayers(){

        getController().callbackObtainOtherPlayers().forEach(CliPrinter::printPersonalBoard);
        showMenuAndAskNonBlocking();

    }
}
