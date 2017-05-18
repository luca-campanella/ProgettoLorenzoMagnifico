package it.polimi.ingsw.protocol;

import it.polimi.ingsw.packet.PacketType;
import it.polimi.ingsw.server.SocketPlayer;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by federico on 18/05/2017.
 */
public class ReadClientPacketProtocol {
    /**
     * the player socket that has this protocol
     */
    private SocketPlayer player;
    /**
     * the different instruction to read the packet
     */
    private HashMap<PacketType, FunctionResponse> instruction;
    /**
     * this is the attribute used to obtain the response by the lambda function
     */
    private FunctionResponse response;

    public ReadClientPacketProtocol(SocketPlayer player){
        this.player=player;
        //putIstruction();
    }

    /**
     * this method is used to save all the response based on the packetType
     */
    private void putIstruction(){
        instruction.put(PacketType.LOGIN, ()-> player.loginPlayer());
        instruction.put(PacketType.REGISTER, ()-> player.registerPlayer());
        instruction.put(PacketType.MOVE_IN_TOWER, ()-> player.);
        instruction.put(PacketType.MOVE_IN_MARKET, ()-> player.);
        instruction.put(PacketType.HARVESTING, ()-> player.);
        instruction.put(PacketType.BUILDING, ()-> player.);
        instruction.put(PacketType.DISCARD_LEADER, ()-> player.);
        instruction.put(PacketType.PLAY_LEADER, ()-> player.);
        instruction.put(PacketType.CHAT, ()-> player.floodChatMsg());
        instruction.put(PacketType.END_PHASE, ()-> player.);
    }
    public void doMethod(PacketType packetType){

        response=instruction.get(packetType);

        response.chooseMethod();

    }


}
