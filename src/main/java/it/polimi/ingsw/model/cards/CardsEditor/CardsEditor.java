package it.polimi.ingsw.model.cards.CardsEditor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.TerritoryCard;
import it.polimi.ingsw.model.effects.EffectInterface;
import it.polimi.ingsw.model.effects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.NoEffect;
import it.polimi.ingsw.model.effects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.testingGSON.boardLoader.BoardCreator;
import it.polimi.ingsw.utils.Debug;

import java.io.*;
import java.util.Scanner;

/**
 * Created by higla on 23/05/2017.
 */
public class CardsEditor {

    public static void main(String args[]) {
        ImmediateEffectInterface effect = new NoEffect();
        int i = 0;
        Debug.instance(Debug.LEVEL_VERBOSE);
        Debug.printVerbose("Hello! This is a little program to create cards in JSON and then read it to files. For the moment, this program will create just green cards");
        Debug.printVerbose("Green Cards needs: harvestEffectValue: ");
        //chissene tbh
        AbstractCard tempGreen = new TerritoryCard();
        while(i < 3) {
            Debug.printVerbose("Effetti: ");
            effect = new TakeOrPaySomethingEffect(new Resource(ResourceType.COIN, 2));
            tempGreen.addImmediateEffect(effect);
            i--;
        }
            Gson gson = new GsonBuilder().create();
            Debug.printVerbose(gson.toJson(tempGreen).toString());

    }
}
