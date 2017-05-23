package it.polimi.ingsw.client.controller.datastructure;

/**
 * This class just couples username and password for the user, it should not be sent online
 */
public class UsrPwdContainer {
    private String nickname;
    private String password;

    public UsrPwdContainer(String nickname, String password)
    {
        this.nickname = nickname;
        this.password = password;
    }

   /* public UsrPwdContainer(LoginOrRegisterPacket pkg)
    {
        this.nickname = pkg.getNickname();
        this.password = pkg.getPassword();
    }*/

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
