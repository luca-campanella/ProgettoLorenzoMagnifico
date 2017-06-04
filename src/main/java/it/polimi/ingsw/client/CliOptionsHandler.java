package it.polimi.ingsw.client;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to ask the user a choice between multiple options, described in a String
 */

public class CliOptionsHandler {

    private ArrayList<String> options;
    private Scanner scanner = new Scanner(System.in);

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
        System.out.println("Select an option typing the corresponding number");
        for(int i = 0; i< options.size(); i++)
            System.out.println(i + "--- " + options.get(i));

        numberPicked = scanner.nextInt();

        while(numberPicked > options.size()) {
            System.out.println("Please insert a valid number, between 0 and " + options.size());
            numberPicked = scanner.nextInt();
        }

        return numberPicked;
    }
}
