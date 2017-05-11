package it.polimi.ingsw.packet;

/**
 * packet used to login and to register the player
 */
public class LoginPacket extends Packet{
    /**
     * the nickname used by the player
     */
    private String nickname;
    /**
     * password linked to the nickname
     */
    private String password;

    /**
     *costructor of the class
     */
    public LoginPacket(String type,String nickname,String password){

        this.nickname=nickname;
        this.password=password;
    }

}
