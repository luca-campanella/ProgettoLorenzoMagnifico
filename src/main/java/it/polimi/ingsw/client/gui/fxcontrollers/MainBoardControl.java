package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.cards.AbstractCard;
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


    public void placeCards(List<AbstractCard> cards) {


        for(int col = 0; col < 4; col++) {
            for(int raw = 0; raw < 4; raw++) {
                ImageView imgView = ((ImageView) (towersCouncilFaith.lookup("card"+col+raw)));
                Image cardImg  = new Image(getClass().getResourceAsStream("/imgs/Cards/" + cards.get(raw+col).getImgName()));
                imgView.setImage(cardImg);
            }
        }


    }
}
