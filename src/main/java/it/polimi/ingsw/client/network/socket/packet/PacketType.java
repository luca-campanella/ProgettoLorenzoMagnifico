package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * type of packet that can be send
 */
public enum  PacketType implements Serializable {

    LOGIN, REGISTER, MOVE_IN_TOWER, MOVE_IN_MARKET, HARVESTING, BUILDING, DISCARD_LEADER, PLAY_LEADER, CHAT, END_PHASE,UPDATES;


}
