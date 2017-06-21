package it.polimi.ingsw.server;

import it.polimi.ingsw.client.exceptions.IllegalMoveException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.PersonalTileEnum;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the class that handles a room and offers a layer between the network part of the server and the actual game
 */
public class Room {

    /**
     * Array of order of the players in the room, its dimension is set in the constructor
     */
    private ArrayList<AbstractConnectionPlayer> players;

    private ControllerGame controllerGame;

    private ArrayList<LeaderCard> cardToPlayer;

    /**
     * timeout that starts when the second player joins the room. When time is up game starts. Set by the constructor
     */
    private int timeoutInSec;

    private int maxNOfPlayers;
    private int currNOfPlayers;
    private boolean isGameStarted;

    /**
     * Constructor
     *
     * @param maxNOfPlayers max number of players for this room
     * @param timeoutInSec  timeout that starts when the second player joins the room. When time is up game starts
     */
    public Room(int maxNOfPlayers, int timeoutInSec) {
        this.timeoutInSec = timeoutInSec;
        this.maxNOfPlayers = maxNOfPlayers;
        currNOfPlayers = 0;
        isGameStarted = false;
        players = new ArrayList<>(maxNOfPlayers);
    }


    public boolean isGameStarted() {
        return isGameStarted;
    }

    public boolean canJoin(AbstractConnectionPlayer player) {
        for (AbstractConnectionPlayer i : players) {
            if (i.getNickname().equals(player.getNickname()))
                return false;
        }
        return true;
    }


    /**
     * adds new player to the room, it also binds the player with the instance of the room
     *
     * @param player the istance of the player to add
     */
    public void addNewPlayer(AbstractConnectionPlayer player) {
        players.add(player);
        player.setRoom(this);
        currNOfPlayers++;
        Debug.printDebug("*Room*: added player " + player.getNickname());
        if (currNOfPlayers == maxNOfPlayers) //ModelController should start
        {
            Debug.printVerbose("Room capacity reached, starting new game");
            startGame();
            Debug.printVerbose("Room capacity reached, returned from start function");
        } else if (currNOfPlayers == 2) {
            Debug.printVerbose("2 players reached ");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Debug.printVerbose("Timeout reached, starting new game");
                    startGame();
                    Debug.printVerbose("Timeout reached, returned from start function");
                }
            }, (long) (timeoutInSec) * 1000);
        }
    }

    /**
     * when the time out is ended or the room is full the game start to prepare all the object needed on the game
     */
    private void startGame() {
        Debug.printVerbose("Game on room started");
        isGameStarted = true;
        try {
            Debug.printVerbose("just before constructor");
            controllerGame = new ControllerGame(players, this);
            Debug.printVerbose("after constructor");
            controllerGame.startNewGame();
            Debug.printDebug("New game started, waiting for first player to move");
        } catch (Exception e) {
            Debug.printError("Connection Error", e);
        }
    }

    /**
     * reload the order of player when it changes
     *
     * @param orderPlayers the order of players
     */
    public void updateOrderPlayer(ArrayList<AbstractConnectionPlayer> orderPlayers) {

        this.players = orderPlayers;

    }

    /**
     * This method is called by a the (@link AbstractConnectionPlayer) that wants to send a message (Direction: AbstractConnectionPlayer -> Room)
     *
     * @param player the sender
     * @param msg
     */
    public void floodChatMsg(AbstractConnectionPlayer player, String msg) {
        for (AbstractConnectionPlayer i : players) {
            if (player != i) {//the message should not be sent to the sender
                try {

                    i.receiveChatMsg(player.getNickname(), msg);
                    System.out.println("send message to " + player.getNickname());

                } catch (NetworkException e) { //not a big problem if a chat message is not sent
                    Debug.printError("Unable to sent chat message to " + i.getNickname(), e);
                }
            }
        }
    }

    /**
     * call the method on controller game to place a family member on the council
     *
     * @throws IllegalMoveException if the player doesn't have the correct resources to do the action
     */
    public void placeOnCouncil(FamilyMember familyMember, HashMap<String, Integer> playerChoices){

        controllerGame.placeOnCouncil(familyMember, playerChoices);
        floodPlaceOnCouncil(familyMember, playerChoices);


    }

    /**
     * call the method on controller game to place a family member on the tower
     *
     * @throws IllegalMoveException if the player doesn't have the correct resources to do the action
     */
    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws IllegalMoveException {

        controllerGame.placeOnTower(familyMember, towerIndex, floorIndex, playerChoices);
        floodPlaceOnTower(familyMember, towerIndex, floorIndex, playerChoices);

    }

    /**
     * the method called by the client to do place a family member on the market
     *
     * @throws IllegalMoveException if the player doesn't have the correct resources to do the action
     */
    public void placeOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws IllegalMoveException {

        controllerGame.placeOnMarket(familyMember, marketIndex, playerChoices);
        floodPlaceOnMarket(familyMember, marketIndex, playerChoices);

    }

    /**
     * call the method on controller game to build
     */
    public void build(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices){

        controllerGame.build(familyMember, servant, playerChoices);
        floodBuild(familyMember, servant, playerChoices);

    }

    /**
     * call the method on controller game to harvest
     *
     * @throws IllegalMoveException if the player doesn't have the correct resources to do the action
     */
    public void harvest(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices){

        controllerGame.harvest(familyMember, servant, playerChoices);
        floodHarvest(familyMember, servant, playerChoices);

    }

    /**
     * launch the move of a player to the other players
     */
    private void floodPlaceOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) {

        for (AbstractConnectionPlayer player : players) {
            if (!player.getNickname().equals(familyMember.getPlayer().getNickname())) {
                try {
                    player.receivePlaceOnTower(familyMember, towerIndex, floorIndex, playerChoices);
                } catch (NetworkException e) {
                    Debug.printError("Unable to sent move on tower to " + player.getNickname(), e);
                }
            }
        }
    }

    /**
     * launch the move of a player to the other players
     */
    private void floodBuild(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) {

        for (AbstractConnectionPlayer player : players) {
            if (!player.getNickname().equals(familyMember.getPlayer().getNickname())) {
                try {
                    player.receiveBuild(familyMember, servant, playerChoices);
                } catch (NetworkException e) {
                    Debug.printError("Unable to sent move on build to " + player.getNickname(), e);
                }
            }
        }
    }

    /**
     * launch the move of a player to the other players
     */
    private void floodHarvest(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) {

        for (AbstractConnectionPlayer player : players) {
            if (!player.getNickname().equals(familyMember.getPlayer().getNickname())) {
                try {
                    player.receiveHarvest(familyMember, servant, playerChoices);
                } catch (NetworkException e) {
                    Debug.printError("Unable to sent move on harvest to " + player.getNickname(), e);
                }
            }
        }
    }

    /**
     * launch the move of a player to the other players
     */
    private void floodPlaceOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) {

        for (AbstractConnectionPlayer player : players) {
            if (!player.getNickname().equals(familyMember.getPlayer().getNickname())) {
                try {
                    player.receivePlaceOnMarket(familyMember, marketIndex, playerChoices);
                } catch (NetworkException e) {
                    Debug.printError("Unable to sent move on market to " + player.getNickname(), e);
                }
            }
        }
    }

    public void endPhase(AbstractConnectionPlayer player) throws IllegalMoveException {

        controllerGame.endPhase(player);
        floodEndPhase(player);

    }

    /**
     * launch the end of a player' phase to the other players
     */
    private void floodEndPhase(AbstractConnectionPlayer playerEndPhase) {

        for (AbstractConnectionPlayer player : players) {
            if (player != playerEndPhase) {
                try {
                    player.receiveEndPhase(playerEndPhase);
                } catch (NetworkException e) {
                    Debug.printError("Unable to sent end phase message to " + player.getNickname(), e);
                }
            }
        }
    }

    /**
     * deliver the new dices on the board to all the player
     */
    public void deliverDices(ArrayList<Dice> dices) {

        for (AbstractConnectionPlayer player : players) {

            try {
                player.receiveDices(dices);
            } catch (NetworkException e) {

                Debug.printError("Unable to sent new dices to " + player.getNickname(), e);

            }
        }
    }

    /**
     * deliver the initial game board to the players
     */
    public void receiveStartGameBoard(Board gameBoard) {

        try {
            for (AbstractConnectionPlayer player : players)
                player.receiveStartGameBoard(gameBoard);
        } catch (NetworkException e) {
            Debug.printError("ERROR on the deliver of the board ", e);
        }

    }

    /**
     * inform the player that is his turn to play
     */
    public void playersTurn(AbstractConnectionPlayer player) {

        try {
            player.receiveStartOfTurn();
        } catch (NetworkException e) {
            Debug.printError("ERROR on the deliver of the token ", e);
        }

    }

    /**
     * this method is called by the controller game to deliver all the players to the different players
     */
    public void deliverOrderPlayers(ArrayList<String> orderPlayers) {

        try {
            for (AbstractConnectionPlayer player : this.players)
                player.deliverOrderPlayers(orderPlayers);
        } catch (NetworkException e) {
            Debug.printError("ERROR on the deliver of the players ", e);
        }
    }

    /**
     * this method is used to deliver the leader cards to the different players
     */
    public void initiateLeaderChoice(ArrayList<LeaderCard> leaderCards) {

        for (LeaderCard card : leaderCards) {
            Debug.printVerbose(card.getName());
        }
        cardToPlayer = leaderCards;
        deliverLeaderCardsToPlayers();
    }

    private void deliverLeaderCardsToPlayers() {

        if (cardToPlayer.size() == 0) {
            controllerGame.choseAllTheLeadersCards();
            return;
        }
        if (cardToPlayer.size() % players.size() != 0)
            return;
        int index = 0;

        // numberOfTimesTheChoiceIsDone is the number of times the round of choices of the leaders
        for (int numberOfTimesTheChoiceIsDone = 4 * players.size() - (cardToPlayer.size() / players.size()); index < cardToPlayer.size(); numberOfTimesTheChoiceIsDone++) {
            AbstractConnectionPlayer player = players.get(numberOfTimesTheChoiceIsDone % players.size());
            int numberCardToDeliver = cardToPlayer.size() / players.size();
            ArrayList<LeaderCard> cardToDeliver = new ArrayList<>(4);
            for (int i = 0; i < numberCardToDeliver; i++) {

                cardToDeliver.add(cardToPlayer.get(index++));

            }
            try {
                player.receiveLeaderCards(cardToDeliver);
                cardToDeliver.clear();
            } catch (NetworkException e) {
                Debug.printError("ERROR: cannot deliver the leader cards to " + player);
            }

        }
    }

    public void receiveLeaderCards(LeaderCard leaderCard, AbstractConnectionPlayer player) {
        Debug.printVerbose("[Room] receiveLeaderCards called");
        controllerGame.choiceLeaderCard(leaderCard, player);
        for (LeaderCard card : cardToPlayer) {
            if (card.getName().equals(leaderCard.getName())) {
                cardToPlayer.remove(card);
                Debug.printVerbose("eliminated " + leaderCard.getName());
                break;
            }
        }

        deliverLeaderCardsToPlayers();

    }

    /**
     * this method is called by controllerGame and deliver to all the clients on the room the cards to place on the board
     */
    public void deliverCardToPlace(ArrayList<AbstractCard> cards) {
        for (AbstractConnectionPlayer player : players) {
            try {
                player.deliverCardToPlace(cards);
            } catch (IOException e) {
                Debug.printError("tried to deliver the cards to " + player.getNickname());
            }

        }
    }

    /**
     * @param familyMember
     */
    private void floodPlaceOnCouncil(FamilyMember familyMember, HashMap<String, Integer> playerChoices) {

        for (AbstractConnectionPlayer player : players) {
            if (!familyMember.getPlayer().getNickname().equals(player.getNickname())) {
                try {
                    player.receivePlaceOnCouncil(familyMember, playerChoices);
                } catch (NetworkException e) {
                    Debug.printError("tried to deliver move on council to " + player.getNickname());
                }

            }
        }
    }

    /**
     * this method is used to deliver to the clients the personale tiles he can choose
     */
    public void deliverPersonalTiles(ArrayList<PersonalTile> personalTiles) {

        ArrayList<PersonalTile> personalTilesToDeliver = new ArrayList<>(2);
        int takeStandard = 0;
        int takeSpecial = 0;
        PersonalTile specialTile = null;
        for (AbstractConnectionPlayer player : players) {
            for (PersonalTile personalTile : personalTiles) {

                if (personalTile.getPersonalTileEnum() == PersonalTileEnum.STANDARD && takeStandard == 0){
                    personalTilesToDeliver.add(personalTile);
                    takeStandard = 1;
                }
                else if (personalTile.getPersonalTileEnum() == PersonalTileEnum.SPECIAL && takeSpecial == 0) {
                    personalTilesToDeliver.add(personalTile);
                    specialTile = personalTile;
                    takeSpecial = 1;
                }
            }
            personalTiles.remove(specialTile);
            try{

                player.deliverPersonalTiles(personalTilesToDeliver);
                takeSpecial = 0;
                takeStandard = 0;
                personalTilesToDeliver.clear();

            }
            catch (NetworkException e){
                Debug.printError("Cannot deliver the personale tiles to player : " + player,e);
            }
        }
    }

    /**
     * this method is called by the connection to deliver the choice of the personal tile of the player
     * it deliver the choice to the controller of the game
     * @param personalTile the personal tile chose by the client
     */
    public void chosePersonalTile(PersonalTile personalTile, AbstractConnectionPlayer player) {

        controllerGame.choosePersonalTile(personalTile, player);
        floodChosenPersonalTile(personalTile, player);

    }

    private void floodChosenPersonalTile(PersonalTile personalTile, AbstractConnectionPlayer player){

        for(AbstractConnectionPlayer player1 : players){
            if(!player.getNickname().equals(player1.getNickname())){
                try{
                    player1.otherPlayerPersonalTile(player.getNickname(), personalTile);
                }
                catch (NetworkException e){
                    Debug.printError(e);
                }
            }
        }
    }

    /**
     * this method is called by the controller game to deliver to the client of an error on a move
     * @param playerName
     */
    public void deliverError(String playerName) {

        for(AbstractConnectionPlayer player : players){
            if(player.getNickname().equals(playerName)){
                try{
                    player.deliverErrorMove();
                }
                catch (NetworkException e){
                    Debug.printError("cannot deliver the error move to " + playerName);
                }
            }
        }
    }

    /**
     * this method is called by the client to inform the server that had discrded a leader card
     * @param nameCard the name of the card the player had discarded
     * @param resourceGet the resource obtained
     */
    public void receiveDiscardLeaderCard(String nameCard, HashMap<String, Integer> resourceGet, String nickname) {

        controllerGame.discardLeaderCard(nickname, nameCard, resourceGet);
        floodDiscardLeaderCard(nameCard, resourceGet, nickname);

    }

    /**
     * this method is used to deliver to the other players the action
     * @param nameCard the name of the leader card discarded
     * @param resourceGet the type of resources gotten
     * @param nickname the nickname o the player that had discrded the card
     */
    private void floodDiscardLeaderCard(String nameCard, HashMap<String, Integer> resourceGet, String nickname) {

        for(AbstractConnectionPlayer player : players){
            if(!player.getNickname().equals(nickname)){
                try{
                    player.deliverDiscardLeaderCard(nameCard, nickname, resourceGet);
                }

                catch (NetworkException e){
                    Debug.printError("cannot deliver the leader discarded to " + player.getNickname());
                }
            }
        }
    }
}

