package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.model.player.Player;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * Created by campus on 24/06/2017.
 */
public class MainBoardControl extends CustomFxControl {

    @FXML
    AnchorPane towersCouncilFaith;

    private Board board;

    private Player thisPlayer;

    private List<Player> otherPlayers;


    public void placeCards() {
        Tower[] towers = board.getTowers();

        for(int col = 0; col < towers.length; col++) {
            for(int raw = 0; raw < 4; raw++) {
                ImageView imgView = ((ImageView) (towersCouncilFaith.lookup("card"+col+raw)));
                Image cardImg  = new Image(getClass().getResourceAsStream("/imgs/Cards/" +
                        towers[col].getFloorByIndex(raw).getCard().getImgName()));
                imgView.setImage(cardImg);
                imgView.setPreserveRatio(true);
            }
        }

    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setThisPlayer(Player thisPlayer) {
        this.thisPlayer = thisPlayer;
    }

    public void setOtherPlayers(List<Player> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }
}
