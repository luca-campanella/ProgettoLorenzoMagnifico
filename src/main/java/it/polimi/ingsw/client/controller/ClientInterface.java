package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;

import java.util.ArrayList;

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

}
