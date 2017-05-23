package it.polimi.ingsw.client.network.socket.packet;

import java.io.Serializable;

/**
 * This is the packet used when the client wants either to login or to register. It should come after a LOGIN or a REgister header packet
 */
public class LoginOrRegisterPacket implements Serializable {
    private String nickname;
    private String password;

    public LoginOrRegisterPacket(String nickname, String password)
    {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
