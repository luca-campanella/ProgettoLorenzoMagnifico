package it.polimi.ingsw.enumeration;

import java.io.Serializable;

/**
 * Created by federico on 12/05/2017.
 */
public enum  PacketType implements Serializable {
    LOGIN, REGISTRATION, PLACE_FAMILY_MEMBER, DISCARD_LEADER, CHAT, END_PHASE
}
