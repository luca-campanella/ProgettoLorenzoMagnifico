package it.polimi.ingsw.protocol;

import it.polimi.ingsw.client.SocketClient;
import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.packet.PacketType;

import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 * Created by federico on 21/05/2017.
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
        putIstruction();
    }

    /**
     * this method is used to save all the response based on the packetType
     */
    private void putIstruction() {
        //instruction.put(PacketType.CHAT, () -> client.receiveChatMsg());
        instruction.put(PacketType.REGISTER, () -> client.receiveUpdates());
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
