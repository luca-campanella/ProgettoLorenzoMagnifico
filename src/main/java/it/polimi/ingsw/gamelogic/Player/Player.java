package it.polimi.ingsw.gamelogic.Player;

/**
 * The main player class, no network
 */
public abstract class Player {

    private String nickname;

    public Player()
    {
        super();
    }

    public Player(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
