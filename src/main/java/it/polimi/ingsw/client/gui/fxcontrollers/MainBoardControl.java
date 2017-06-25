package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.util.List;

/**
 * Created by campus on 24/06/2017.
 */
public class MainBoardControl extends CustomFxControl {

    @FXML
    AnchorPane towersCouncilFaith;

    @FXML
    AnchorPane marketBuildHarvest;

    @FXML
    Button blueCardsButton;

    @FXML
    Button purpleCardsButton;

    private Board board;

    private Player thisPlayer;

    private List<Player> otherPlayers;

    private List<Dice> dices;


    public void displayCards() {
        Tower[] towers = board.getTowers();

        for(int col = 0; col < towers.length; col++) {
            for(int raw = 0; raw < 4; raw++) {
                ImageView imgView = ((ImageView) (towersCouncilFaith.lookup("#card"+col+raw)));
                Image cardImg  = new Image(getClass().getResourceAsStream("/imgs/Cards/" +
                        towers[col].getFloorByIndex(raw).getCard().getImgName()));
                imgView.setImage(cardImg);
                imgView.setPreserveRatio(true);
            }
        }
        //todo remove, this is just for debug
        CliPrinter.printBoard(board);
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

    public void setDices(List<Dice> dices) {
        this.dices = dices;
    }

    public void displayDices() {
        for(Dice diceIter : dices) {
            if(diceIter.getColor() != DiceAndFamilyMemberColorEnum.NEUTRAL) {
                Text diceText = ((Text) (marketBuildHarvest.lookup("#dice" + diceIter.getColor().getIntegerValue())));
                diceText.setText(String.valueOf(diceIter.getValue()));
            }
        }
        //todo remove, this is just for debug
        CliPrinter.printPersonalBoard(thisPlayer);
    }

    public void displayThisPlayerPersonalBoard() {
        PersonalBoard persBoard = thisPlayer.getPersonalBoard();

        //we enable or disable the buttons to see blue and purple cards if the player has or has not some of them
        purpleCardsButton.setDisable((persBoard.getNumberOfColoredCard(CardColorEnum.PURPLE) == 0));
        blueCardsButton.setDisable((persBoard.getNumberOfColoredCard(CardColorEnum.BLUE) == 0));
    }

    @FXML
    public void showPurpleCards() {
        showCards(thisPlayer.getPersonalBoard().getCardListByColor(CardColorEnum.PURPLE), "Purple cards");
    }

    @FXML
    public void showBlueCards() {
        showCards(thisPlayer.getPersonalBoard().getCardListByColor(CardColorEnum.BLUE), "Blue cards");
    }

    /**
     * Shows a window with the list of cards passed as an argument
     * @param cards the cards to show to the user
     */
    private void showCards(List<? extends AbstractCard> cards, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        if(cards.isEmpty()) {
            alert.setHeaderText("No cards to show");
        } else {
            alert.setHeaderText(null);
            //alert.setContentText(errorDescription);

            HBox cardsContainer = new HBox();
            cardsContainer.setSpacing(5);
            cardsContainer.setAlignment(Pos.CENTER);

            for (AbstractCard cardIter : cards) {
                final Image cardImage = new Image(getClass().getResourceAsStream("/imgs/Cards/" + cardIter.getImgName()));
                final ImageView imgView = new ImageView();
                imgView.setImage(cardImage);
                imgView.setPreserveRatio(true);

                cardsContainer.getChildren().add(imgView);
                alert.setGraphic(cardsContainer);
            }
        }
        alert.initStyle(StageStyle.UTILITY);
        //alert.initOwner(currentStage);
        alert.show();
    }
}
