package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to ask the user a choice between multiple options, described in a String
 */

public class CliOptionsHandler {

    private List<String> options;

    private String initialMessage = null;

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
     * @param options are the options available
     */
    public CliOptionsHandler(List<String> options) {
        this.options = options;
    }

    /**
     * this constructor should be called when we already know how many options we'll add later on
     * @param numberOfOptions is the number of options available
     */
    public CliOptionsHandler(int numberOfOptions) {
        options = new ArrayList<>(numberOfOptions);
    }

    /**
     * this constructor should be called when we already know how many options we'll add later on
     * and we want to add an initial message, such as the question
     * @param numberOfOptions is the number of options available
     * @param initialMessage the initial message, such as the question
     */
    public CliOptionsHandler(String initialMessage, int numberOfOptions) {
        this.initialMessage = initialMessage;
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
    public void addEffectsArrayList(List<? extends ImmediateEffectInterface> effectOptions) {
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
        if(initialMessage != null)
            CliPrinter.println(initialMessage);
        CliPrinter.println("Select an option typing the corresponding number");
        for(int i = 0; i< options.size(); i++)
            CliPrinter.println(i + "--- " + options.get(i));

        numberPicked = StdinSingleton.readAndParseInt();

        while(numberPicked >= options.size() || numberPicked < 0) {
            CliPrinter.println("Please insert a valid number, between 0 and " + (options.size() - 1));
            numberPicked = StdinSingleton.readAndParseInt();
        }

        return numberPicked;
    }

    //Just for testing
    public static void main(String[] args)
    {
        StdinSingleton.instance();
        CliOptionsHandler cliOptionsHandler = new CliOptionsHandler(4);

        for(int i = 0; i < 4; i++)
            cliOptionsHandler.addOption("Option number " + i);

        int choice = cliOptionsHandler.askUserChoice();

        CliPrinter.println("The user chose option number: " + choice);
    }
}
