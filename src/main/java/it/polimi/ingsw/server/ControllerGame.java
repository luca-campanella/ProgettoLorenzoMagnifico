package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.client.controller.ControllerModelInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.controller.ModelController;
import it.polimi.ingsw.model.effects.immediateEffects.GiveCouncilGiftEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.testingGSON.boardLoader.BoardCreator;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * the controller of the game on the server
 */
public class ControllerGame  implements ControllerModelInterface {
    private ControllerGame game;
    private Room room;
    private Deck deck;
    private Board boardGame;
    private ModelController modelController;
    private int numberOfPlayers;
    private int numberOfRound;
    private int numberOfTurn;
    private HashMap<String, Integer> playerChoices;
    private ArrayList<AbstractConnectionPlayer> players;

    public static void main(String[] args) throws Exception {
        ControllerGame controllerGame =  new ControllerGame(2);
        CliPrinter cli = new CliPrinter();
        //cli.printDeck(controllerGame.getDeck());
        cli.printBoard(controllerGame.getBoardGame());
    }
    private  Deck getDeck()
    {
        return deck;
    }
    public void endTurn(){

        numberOfTurn++;

        if(numberOfTurn >= numberOfPlayers*4 && numberOfRound == 3)
            modelController.endGame();

        if(numberOfTurn >= numberOfPlayers*4){
            modelController.prepareForNewRound();
            reDoOrderPlayer(modelController.getFamilyMemberCouncil());
            numberOfTurn = 0;
            numberOfRound++;

        }

    }

    /**
     * manage the order of the players based on the council
     * @param familyMembers the family members placed on the council
     */
    private void reDoOrderPlayer(ArrayList<FamilyMember> familyMembers){

        ArrayList<AbstractConnectionPlayer> newPlayersOrder = new ArrayList<>(players.size());

        for(FamilyMember i : familyMembers){

            for(AbstractConnectionPlayer player : players){

                if(i.getPlayer().getNickname().equals(player.getNickname())){

                    newPlayersOrder.add(player);
                    players.remove(i.getPlayer());

                }
            }
        }

        newPlayersOrder.addAll(players);
        players.clear();
        players = newPlayersOrder;
        room.updateOrderPlayer(players);

    }

    public Board getBoardGame() {
        return boardGame;
    }

    /**
     * This method creates a new board and modifies it considering the number of orderPlayers
     * @param players the payers that are on the game
     * @param room the room where the game is located
     * @throws Exception if file where Board configuration is
     */
    public ControllerGame(ArrayList<AbstractConnectionPlayer> players, Room room) throws Exception {
        JSONLoader jsonLoader = new JSONLoader();
        boardGame = jsonLoader.boardCreator();
        deck = jsonLoader.createNewDeck();
        numberOfPlayers = players.size();
        boardModifier(numberOfPlayers);
        this.room = room;
        this.players = players;
        modelController = new ModelController(players, boardGame);
        numberOfTurn = 0;
        numberOfRound = 1;
        playerChoices = new HashMap<>(10);
    }

    /**
     * costructor for controllerGame
     * @param numberOfPlayers
     * @throws Exception
     */
    public ControllerGame(int numberOfPlayers) throws Exception {
        JSONLoader jsonLoader = new JSONLoader();
        boardGame = jsonLoader.boardCreator();
        deck = jsonLoader.createNewDeck();
        int period = 1;
        boardGame = deck.fillBoard(boardGame, period);
        boardModifier(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;

    }

    /**
     * this method modifies the board given a number of orderPlayers
     * @param numberOfPlayers the number of orderPlayers that will play on this game
     */
    private void boardModifier(int numberOfPlayers)
    {
        if(numberOfPlayers == 4)
            return;
        boardThreePlayers();
        if(numberOfPlayers == 3)
            return;
        boardTwoPlayers();
    }


    /**
     * this method modifies the board when there are three orderPlayers
     */
    private void boardThreePlayers()
    {
        boardGame.getMarket().remove(3);
        boardGame.getMarket().remove(2);
    }

    /**
     * This method modifies the board if there are 2 orderPlayers.
     */
    private void boardTwoPlayers()
    {
        boardGame.getHarvest().setTwoPlayersOneSpace(true);
        boardGame.getBuild().setTwoPlayersOneSpace(true);
    }


    /**
     * call the method on the controller of the model to start a new game
     */
    public void startNewGame(){

        modelController.startNewGame();

        chooseOrderRandomly();

        //add the coins to the players based on the order of turn
        modelController.addCoinsStartGame(players);

    }

    /**
     * choose the order of the players at the beginning of the game
     */
    private void chooseOrderRandomly(){

        ArrayList<AbstractConnectionPlayer> playersOrder = new ArrayList<>(players.size());
        Random random = new Random();
        int valueIndex;
        for(int i=0; i<players.size();){

            valueIndex = random.nextInt(players.size());
            //add the player of the index
            playersOrder.add(players.get(random.nextInt(valueIndex)));
            //remove the player of the index
            players.remove(valueIndex);

        }
        players.addAll(playersOrder);

        room.updateOrderPlayer(players);

    }

    /**
     * call the method on the controller of the model to place on the tower
     * @param familyMember the familymember the player places
     * @param towerIndex the number of the tower where the family member is placed
     * @param floorIndex the number of floor on the tower the family member is placed
     */
    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex){

        modelController.placeOnTower(familyMember, towerIndex, floorIndex);

    }

    /**
     * call the method on the controller of the model to harvest
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     */
    public void harvest(FamilyMember familyMember, int servant){

        modelController.harvest(familyMember, servant);

    }

    /**
     * call the method on the controller of the model to build
     * @param familyMember the familymember the player places
     * @param servant the number of servant you add on the family member to increase the value
     */
    public void build(FamilyMember familyMember, int servant){

        modelController.build(familyMember, servant);

    }

    /**
     * call the method on the controller of the model to place on the market
     * @param familyMember the familymember the player places
     * @param marketSpaceIndex the number of servant you add on the family member to increase the value
     */
    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex){

        modelController.placeOnMarket(familyMember, marketSpaceIndex);

    }

    /**
     * call the method on the controller of the model to discard a leader card
     * @param player the player who has the card
     * @param nameLeader the name of the leader card
     */
    public void discardLeaderCard(Player player, String nameLeader){

        modelController.discardLeaderCard(player, nameLeader);

    }

    /**
     * call the method on the controller of the model to activate a leader card
     * @param player the player who has the card
     * @param nameLeader the name of the leader card
     */
    public void playLeaderCard(Player player, String nameLeader){

        modelController.activateLeaderCard(player, nameLeader);

    }

    public int choose(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer){

        return playerChoices.get(nameCard);

    }


}

