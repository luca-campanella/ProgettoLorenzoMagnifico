package it.polimi.ingsw.model.board;

/**
 * Enumeration of card colors
 */
public enum CardColorEnum {
    GREEN("Green", "Territory", 0),
    BLUE("Blue", "Character", 1),
    YELLOW("Yellow", "Building", 2),
    PURPLE("Purple", "Venture", 3);

    private String cardColor;
    private String cardType;
    private int indexOfTower;

    private CardColorEnum(String cardColor, String cardType, int indexOfTower) {
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.indexOfTower = indexOfTower;
    }

    public String getCardColor() {
        return cardColor;
    }

    public int getIndexOfTower(){return indexOfTower;}

    public String getCardType() {
        return cardType;
    }

    public String getFullDescription() {
        return cardColor + " (" + cardType + ") " + "card";
    }
}
