package it.polimi.ingsw.model.board;

import it.polimi.ingsw.controller.network.socket.protocol.FunctionResponse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by higla on 16/05/2017.
 */
public class Market {

    //the different spaces on the market
    private ArrayList<MarketAS> marketSpaces;
    private HashMap<Integer,FunctionResponse> loadPlaces;
    private FunctionResponse loadMarket;

    public Market(int numPlayers){
        marketSpaces = new ArrayList<>(5);
        loadPlaces = new HashMap<>(3);
        loadFunction();
        prepareMarket(numPlayers);
    }

    private void loadFunction(){

        loadPlaces.put(2,this::loadMarket2);
        loadPlaces.put(3,this::loadMarket3);
        loadPlaces.put(4,this::loadMarket4);
        loadPlaces.put(5,this::loadMarket5);

    }

    private void prepareMarket(int numPlayers){
        loadMarket = loadPlaces.get(numPlayers);
        loadMarket.chooseMethod();
    }

    private void loadMarket2(){

    }

    private void loadMarket3(){

        loadMarket2();;
    }

    private void loadMarket4(){

        loadMarket3();
    }

    private void loadMarket5(){

        loadMarket4();
    }


}
