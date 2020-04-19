package it.polimi.ingsw.Model;

/**
 * Represents the different type of blocks.
 */

public enum Block {

    TERRAIN(0), LONE(1), LTWO(2), LTHREE(3), DOME(4);

    private final int value;

    /**
     * @param value each value represents the level. Range from 0 (TERRAIN) to 4 (DOME)
     */
    Block(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
