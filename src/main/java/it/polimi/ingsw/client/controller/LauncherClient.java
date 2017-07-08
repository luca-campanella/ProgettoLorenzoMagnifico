package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.cli.CommandLineUI;
import it.polimi.ingsw.client.cli.StdinSingleton;
import it.polimi.ingsw.client.gui.GraphicalUI;

/**
 * This class is the temporary launcher of Lorenzo's il magnifico
 */
public class LauncherClient {
    private ClientMain clientMain;
    /**
     * This method welcomes the user and asks him if he wants CLI or gui.
     * @return the correct object chosen by the player
     */
    public AbstractUIType welcome()
    {
        CliPrinter.println("Hello! WELCOME to Lorenzo il Magnifico's temporary launcher");
        return this.inputCliOrGui();

    }
    public LauncherClient(ClientMain client)
    {
        this.clientMain = client;
    }

    /**
     * This method chooses if UI is CL or Graphical.
     * @return the correct object choosen by the player
     */
    private AbstractUIType inputCliOrGui()
    {
        String input;
        while(true){
            CliPrinter.println("Do you want to use CLI or gui?");
            input = StdinSingleton.nextLine();


            if(input.equalsIgnoreCase("cli")){
                return new CommandLineUI(this.clientMain);
            }
            if(input.equalsIgnoreCase("gui")){
                return new GraphicalUI(this.clientMain);
            }
        }

    }
}

