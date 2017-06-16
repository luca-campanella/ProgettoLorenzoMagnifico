package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * type of packet that can be send
 */
public enum  PacketType implements Serializable {

    LOGIN, REGISTER, MOVE_IN_TOWER, MOVE_IN_MARKET, HARVEST, BUILD, DISCARD_LEADER, PLAY_LEADER, CHAT, END_PHASE,
    DICE, GAME_BOARD, START_TURN, ORDER_PLAYERS, NICKNAME, LEADER_CHOICES, CARD_TO_PLACE;

}
