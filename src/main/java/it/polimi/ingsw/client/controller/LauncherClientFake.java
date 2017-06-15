package it.polimi.ingsw.client.controller;
import it.polimi.ingsw.client.cli.CommandLineUI;

import java.util.Scanner;

/**
 * todo: implement a proper launcher
 * This class is the temporary launcher of Lorenzo's il magnifico
 */
public class LauncherClientFake {
    ClientMain clientMain;
    /**
     * This method welcomes the user and asks him if he wants CLI or gui.
     * @return the correct object choosen by the player
     */
    public AbstractUIType welcome()
    {
        System.out.println("Hello! WELCOME to Lorenzo il Magnifico's temporary launcher");
        return this.inputCliOrGui();

    }
    public LauncherClientFake(ClientMain client)
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
        Scanner inputScanner = new Scanner(System.in);
        while(true){
            System.out.println("Do you want to use CLI or gui?");
            input = inputScanner.nextLine();

            //inputScanner.close();
            if(input.equalsIgnoreCase("cli")){
                return new CommandLineUI(this.clientMain);
            }
            if(input.equalsIgnoreCase("gui")){
                //return new GraphicalUI(this.clientMain);

            }
        }

    }
}

