package it.polimi.ingsw.client;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * this class handles all options 
 */

public class CliOptionsHandler {
    ArrayList<String> options = new ArrayList<String>();
    Scanner scanner = new Scanner(System.in);

    /**
     * this is the constructor of the class. Since it's a view class, it only manages Strings
     * @param effectOptions so when a list of effects comes, it translates that in strings
     */

    public CliOptionsHandler(ArrayList<? extends ImmediateEffectInterface> effectOptions){
        for(int i=0; i<effectOptions.size(); i++)
            this.addOption(effectOptions.get(i).descriptionShortOfEffect() , i);
    }

    /**
     * this method add options to the choice.
     * @param option is the String list of options that someone has
     * @param indexOfOption it's the index of the option
     */
    //todo: c'è corrispondenza tra questo indice e quello passato? --Arto
    public void addOption(String option, int indexOfOption){
        options.set(indexOfOption, option);
    }

    /**
     * this method ask user to choose an option
     * @return it returns the index of the option
     */
    public int askUserChoice(){
        int numberPicked = options.size() + 2;
        System.out.println("Select an option typing the right number");
        for(int i = 0; i< options.size(); i++)
            System.out.println("Option (" + i + ")" + options.get(i));
        while(numberPicked > options.size())
            numberPicked = scanner.nextInt();
        return numberPicked;
    }
}
