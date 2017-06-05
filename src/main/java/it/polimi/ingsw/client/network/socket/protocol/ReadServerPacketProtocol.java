package it.polimi.ingsw.client.network.socket.protocol;

import it.polimi.ingsw.client.network.socket.SocketClient;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.network.socket.packet.PacketType;
import it.polimi.ingsw.server.network.socket.protocol.FunctionResponse;

import java.util.HashMap;

/**
 * this is the protocol that the client uses to read the placket delivered by the server
 */
public class ReadServerPacketProtocol {
    /**
     * the player socket that has this protocol
     */
    private SocketClient client;
    /**
     * the different instruction to read the packet
     */
    private HashMap<PacketType, FunctionResponse> instruction;
    /**
     * this is the attribute used to obtain the response by the lambda function
     */
    private FunctionResponse response;

    public ReadServerPacketProtocol(SocketClient client){
        this.client=client;
        instruction = new HashMap<>(2);
        putIstruction();
    }

    /**
     * this method is used to save all the response based on the packetType
     */
    private void putIstruction() {
        instruction.put(PacketType.CHAT, () -> client.receiveChatMsg());
        instruction.put(PacketType.DICES, () -> client.receiveDices());
        instruction.put(PacketType.MOVE_IN_TOWER, ()-> client.receivePlaceOnTower());
        instruction.put(PacketType.MOVE_IN_MARKET, ()-> client.receivePlaceOnMarket());
        instruction.put(PacketType.HARVEST, ()-> client.receiveHarvest());
        instruction.put(PacketType.BUILD, ()-> client.receiveBuild());
        //instruction.put(PacketType.DISCARD_LEADER, ()-> client.receiveDiscardCard());
        //instruction.put(PacketType.PLAY_LEADER, ()-> client.receivePlayCard());
        instruction.put(PacketType.END_PHASE, ()-> client.receiveEndPhase());
    }

    /**
     * this method is used to find the response based on the Packet type
     * @param packetType is like a header of the true packet, is used to understand how deserialize the follow packet
     */
    public void doMethod(PacketType packetType) throws NetworkException{

        response=instruction.get(packetType);
        response.chooseMethod();

    }
}
