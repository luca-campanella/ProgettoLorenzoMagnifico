package it.polimi.ingsw.server.network.socket.protocol;

import it.polimi.ingsw.client.network.socket.packet.PacketType;
import it.polimi.ingsw.server.network.socket.SocketPlayer;

import java.util.HashMap;

/**
 * this is the protocol to read the client' s packet telling the server what to do
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
        instruction = new HashMap<>(10);
        putInstruction();
    }

    /**
     * this method is used to save all the response based on the packetType
     */
    private void putInstruction(){
        instruction.put(PacketType.LOGIN, ()-> player.loginPlayer());
        instruction.put(PacketType.REGISTER, ()-> player.registerPlayer());
        instruction.put(PacketType.MOVE_IN_TOWER, ()-> player.placeOnTower());
        instruction.put(PacketType.MOVE_IN_MARKET, ()-> player.placeOnMarket());
        instruction.put(PacketType.HARVEST, ()-> player.harvest());
        instruction.put(PacketType.BUILD, ()-> player.build());
        instruction.put(PacketType.DISCARD_LEADER, ()-> player.discardLeaderCard());
        instruction.put(PacketType.PLAY_LEADER, ()-> player.receivedPlayLeaderCard());
        instruction.put(PacketType.CHAT, ()-> player.floodChatMsg());
        instruction.put(PacketType.END_PHASE, ()-> player.endPhase());
        instruction.put(PacketType.LEADER_CHOICES, ()-> player.deliverLeaderCards());
        instruction.put(PacketType.MOVE_IN_COUNCIL, ()-> player.placeOnCouncil());
        instruction.put(PacketType.CHOSE_TILES, ()-> player.receivedPersonalTile());
        instruction.put(PacketType.ACTIVATE_LEADER, ()-> player.receiveActivatedLeader());
    }

    /**
     * this method is used to find the response based on the Packet type
     * @param packetType is like a header of the true packet, is used to understand how deserialize the follow packet
     */
    public void doMethod(PacketType packetType){

        response=instruction.get(packetType);
        response.chooseMethod();

    }


}
