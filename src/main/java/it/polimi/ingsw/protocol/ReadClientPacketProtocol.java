package it.polimi.ingsw.protocol;

import it.polimi.ingsw.packet.PacketType;
import it.polimi.ingsw.server.SocketPlayer;

import java.util.HashMap;

/**
 * Created by federico on 18/05/2017.
 */
public class ReadClientPacketProtocol {
    private SocketPlayer player;
    /**
     * the different istruction to read the packet
     */
    private HashMap<PacketType,SocketPlayer> istruction;
    public ReadClientPacketProtocol(SocketPlayer player){
        this.player=player;
        //putIstruction();
    }

    /**
     * this method is used to save all the response based on the packetType
     */
    /*private void putIstruction(){
        istruction.put(PacketType.LOGIN, );
        istruction.put(PacketType.REGISTER, );
        istruction.put(PacketType.MOVE_IN_TOWER, );
        istruction.put(PacketType.MOVE_IN_MARKET, );
        istruction.put(PacketType.HARVESTING, );
        istruction.put(PacketType.BUILDING, );
        istruction.put(PacketType.DISCARD_LEADER, );
        istruction.put(PacketType.PLAY_LEADER, );
        istruction.put(PacketType.CHAT, );
        istruction.put(PacketType.END_PHASE, );
    }*/


}
