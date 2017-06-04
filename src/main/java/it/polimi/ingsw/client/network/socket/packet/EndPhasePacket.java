package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.server.network.AbstractConnectionPlayer;

/**
 * the packet delivered during the end of a phase
 */
public class EndPhasePacket {

    private AbstractConnectionPlayer player;

    public EndPhasePacket(AbstractConnectionPlayer player){

        this.player = player;

    }

    public AbstractConnectionPlayer getPlayer(){

        return player;

    }
}
