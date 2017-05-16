package it.polimi.ingsw.packet;

import it.polimi.ingsw.protocol.PacketType;

import java.io.Serializable;

/**
 * abstract class of the differents type of Packet
 */
public abstract class Packet implements Serializable {
    /**
     * type of the packet
     */
    private PacketType packetType;
    void setPacketType(PacketType packetType){
       this.packetType=packetType;
        }
}

