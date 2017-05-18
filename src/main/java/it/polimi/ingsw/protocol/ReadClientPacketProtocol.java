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
    private HashMap<PacketType, FunctionResponse> istruction;
    public ReadClientPacketProtocol(SocketPlayer player){
        this.player=player;
        //putIstruction();
    }

    /**
     * this method is used to save all the response based on the packetType
     */
    private void putIstruction(){
        istruction.put(PacketType.LOGIN, ()-> player.);
        istruction.put(PacketType.REGISTER, ()-> player.);
        istruction.put(PacketType.MOVE_IN_TOWER, ()-> player.);
        istruction.put(PacketType.MOVE_IN_MARKET, ()-> player.);
        istruction.put(PacketType.HARVESTING, ()-> player.);
        istruction.put(PacketType.BUILDING, ()-> player.);
        istruction.put(PacketType.DISCARD_LEADER, ()-> player.);
        istruction.put(PacketType.PLAY_LEADER, ()-> player.);
        istruction.put(PacketType.CHAT, ()-> player.floodChatMsg());
        istruction.put(PacketType.END_PHASE, ()-> player.);
    }


}
