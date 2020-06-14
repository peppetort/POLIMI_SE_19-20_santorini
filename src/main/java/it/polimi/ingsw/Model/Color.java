package it.polimi.ingsw.Model;

/**
 * All possible color that represent a player
 */
public enum Color {

    BLUE(1),RED(2),GREEN(3);

    private final int value;

    Color(int value){
        this.value = value;
    }

    /**
     * @return the value associated with the color
     */
    public int getValue() {
        return value;
    }
}
