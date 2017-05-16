package it.polimi.ingsw.protocol;

import java.io.Serializable;

/**
 * Created by federico on 12/05/2017.
 */
public enum  PacketType implements Serializable {
    LOGIN, REGISTER, PLACE_FAMILY_MEMBER, DISCARD_LEADER, CHAT, END_PHASE
}
