package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.server.network.AbstractConnectionPlayer;

import java.io.Serializable;

/**
 * the packet delivered during the end of a phase
 */
public class EndPhasePacket implements Serializable{

    private String nickname;

    public EndPhasePacket(String nickname){

        this.nickname = nickname;

    }

    public String getNickname(){

        return nickname;

    }
}
