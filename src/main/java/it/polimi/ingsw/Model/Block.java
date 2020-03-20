package it.polimi.ingsw.Model;



public enum Block {

    TERRAIN(0),LONE(1),LTWO(2),LTHREE(3),DOME(4);

    private int value;
    private Block(int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
