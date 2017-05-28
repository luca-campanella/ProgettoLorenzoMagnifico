package it.polimi.ingsw.model.board;

/**
 * Enumeration of card colors
 */
public enum CardColorEnum {
    GREEN("Green", "Territory"),
    BLUE("Blue", "Character"),
    YELLOW("Yellow", "Building"),
    PURPLE("Purple", "Venture");

    private String cardColor, cardType;

    private CardColorEnum(String cardColor, String cardType) {
        this.cardColor = cardColor;
        this.cardType = cardType;
    }

    public String getCardColor() {
        return cardColor;
    }

    public String getCardType() {
        return cardType;
    }

    public String getFullDescription() {
        return cardColor + " (" + cardType + ") " + "card";
    }
}
