package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.utils.Debug;

import java.util.Scanner;

/**
 * This singleton class is used to wrap just once the System.in
 * No other scanner should be opened on System.in
 */
public class StdinSingleton
{


    /**
     * The instance of the singleton
     */
    private static StdinSingleton instance = null;

    /**
     * The corresponding scanner
     */
    private static Scanner stdinScanner;

    /**
     * private constructor following singleton pattern
     */
    private StdinSingleton(){
        stdinScanner = new Scanner(System.in);
    }

    /**
     * Method to instance the object following singleton pattern
     * @return The instance of the singleton
     */
    public static StdinSingleton instance(){
        if(instance == null)
            instance = new StdinSingleton();
        return instance;
    }

    /**
     * Use this method to get the scanner and read from System.in
     * @return the corresponding scanner
     */
    public static Scanner getScanner() {
        return stdinScanner;
    }

    public static String nextLine() {
        return stdinScanner.nextLine();
    }

    /**
      * reads a line from the console and tries to parse it as an integer, if it cannot returns -1
      * @return the integer read or -1 if error
      */
    public static int readAndParseInt() {
            String line = stdinScanner.nextLine();
            try{
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                Debug.printVerbose("Not entered a number");
                return -1;
            }
    }
}
