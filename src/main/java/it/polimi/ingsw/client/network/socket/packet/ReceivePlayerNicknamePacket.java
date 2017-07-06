package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * This packet is used to deliver the nickname of a player, the header brings the information on what to do
 */
public class ReceivePlayerNicknamePacket implements Serializable {
    String nickname;

    public ReceivePlayerNicknamePacket(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
