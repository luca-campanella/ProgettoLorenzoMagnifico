package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.server.network.AbstractConnectionPlayer;

/**
 * the packet delivered during the end of a phase
 */
public class EndPhasePacket {

    private String nickname;

    public EndPhasePacket(String nickname){

        this.nickname = nickname;

    }

    public String getNickname(){

        return nickname;

    }
}
