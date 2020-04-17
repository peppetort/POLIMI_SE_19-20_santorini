package it.polimi.ingsw.Model;

public enum Color {

    BLUE(1),RED(2),GREEN(3);

    private int value;

    private Color(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
