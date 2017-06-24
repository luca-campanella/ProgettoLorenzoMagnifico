package it.polimi.ingsw.model.board;

import it.polimi.ingsw.server.network.socket.protocol.FunctionResponse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class is the market board, the place where all the market places are
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

    public MarketAS getMarket(int position){
        return marketSpaces.get(position);
    }

    public ArrayList<MarketAS> getMarketSpaces() {
        return marketSpaces;
    }
}
