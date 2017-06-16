package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;

/**
 * This class is used to ask the user a choice between multiple options, described in a String
 */

public class CliOptionsHandler {

    private ArrayList<String> options;

    /**
     * this is the constructor of the class used to ask choices between effects.
     * @param effectOptions so when a list of effects comes, it translates that in strings
     */
    /*public CliOptionsHandler(ArrayList<ImmediateEffectInterface> effectOptions){
        options = new ArrayList<String>(effectOptions.size());
        for(int i=0; i<effectOptions.size(); i++)
            this.addOption(effectOptions.get(i).descriptionShortOfEffect() , i);
    }*/

    /**
     * Constructor with all the options already in place
     * @param options
     */
    public CliOptionsHandler(ArrayList<String> options) {
        this.options = options;
    }

    /**
     * this constructor should be called when we already know how many options we'll add later on
     * @param numberOfOptions
     */
    public CliOptionsHandler(int numberOfOptions) {
        options = new ArrayList<>(numberOfOptions);
    }

    /**
     * this constructor should be called only when no information on how many options we are going to add
     */
    public CliOptionsHandler() {
        options = new ArrayList<>(2);

    }

    /**
     * This method is called create choices between effects
     * @param effectOptions the effect options
     */
    public void addEffectsArrayList(ArrayList<? extends ImmediateEffectInterface> effectOptions) {
        for (ImmediateEffectInterface effectIter : effectOptions)
            this.addOption(effectIter.descriptionShortOfEffect());
    }

    /**
     * this method modifies an option.
     * @param option is the String list of options that someone has
     */
    public void modifyOption(String option, int indexOfOption){
        options.set(indexOfOption, option);
    }

    /**
     * Adds an option to the end of the list
     * @param option description of the option
     */
    public void addOption(String option) {
        options.add(option);
    }

    /**
     * this method ask user to choose an option
     * @return it returns the index of the option
     */
    public int askUserChoice(){
        int numberPicked;

        if(options.size() == 1) //if the user has no choices we return immediately
            return 0;

        System.out.println("Select an option typing the corresponding number");
        for(int i = 0; i< options.size(); i++)
            System.out.println(i + "--- " + options.get(i));

        numberPicked = readAndParseInt();

        while(numberPicked >= options.size() || numberPicked < 0) {
            System.out.println("Please insert a valid number, between 0 and " + (options.size() - 1));
            numberPicked = readAndParseInt();
        }

        return numberPicked;
    }

    /**
     * reads a line from the console and tries to parse it as an integer, if it cannot returns -1
     * @return the integer read or -1 if error
     */
    private int readAndParseInt(){
        String line = StdinSingleton.getScanner().nextLine();
        try{
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            Debug.printVerbose("Not entered a number");
            return -1;
        }
    }

    //Just for testing
    public static void main(String[] args)
    {
        CliOptionsHandler cliOptionsHandler = new CliOptionsHandler(4);

        for(int i = 0; i < 4; i++)
            cliOptionsHandler.addOption("Opzione numero " + i);

        int choice = cliOptionsHandler.askUserChoice();

        System.out.println("The user chose option number: " + choice);
    }
}
