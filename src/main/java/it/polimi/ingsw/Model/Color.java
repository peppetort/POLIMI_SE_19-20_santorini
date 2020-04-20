package it.polimi.ingsw.Model;

public enum Color {

    BLUE(1),RED(2),GREEN(3);

    private final int value;

    Color(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
