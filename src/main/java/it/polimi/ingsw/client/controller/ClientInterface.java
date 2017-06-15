package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.PersonalTile;

import java.util.ArrayList;
import java.util.List;

/**
 *   This interface is implemented by {@link ClientMain} to let {@link it.polimi.ingsw.client.network.AbstractClientType} make calls on controller
 */
public interface ClientInterface {

    /**
     * This method is called by {@link it.polimi.ingsw.client.network.AbstractClientType} to display an incoming chat message (Direction: AbstractClientType -> ClientMain; general direction: Server -> Client)
     *
     * @param senderNick
     * @param msg
     */
    public void receiveChatMsg(String senderNick, String msg);

    public void setNickname(String nickname);

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType} to signal that the game has started
     * and to pass to every client the order of players
     * @param orderPlayers the nicknames of the players, in order
     */
    public void receivedOrderPlayers(ArrayList<String> orderPlayers);

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType} to signal that the game has started
     * and to pass the board the server has created
     * @param board the board from the server
     */
    public void receivedStartGameBoard(Board board);

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType} to signal that the game has started
     * and the dices already thrown
     * @param dices the board from the server
     */
    public void receivedDices(ArrayList<Dice> dices);

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that the player is in turn and should move
     */
    public void receivedStartTurnNotification();


    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that the has to pick a leader card between the ones proposed
     * @param leaderCards options
     */
    public void receivedLeaderCards(List<LeaderCard> leaderCards);

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that the has to pick a personal tile between the ones proposed
     * Corresponding callback: {@link ControllerCallbackInterface#callbackOnPersonalTileChosen}
     * @param standardTile option1
     * @param specialTile option2
     */
    public void receivedPersonalTiles(PersonalTile standardTile, PersonalTile specialTile);
}
