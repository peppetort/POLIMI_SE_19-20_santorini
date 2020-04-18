package it.polimi.ingsw.Model;

/**
 * Rappresenta i vari blocchi che si possono costruire in una cella del campo di gioco.
 * Viene utilizzata nella classe contenitore {@link Box}
 */

public enum Block {

    TERRAIN(0), LONE(1), LTWO(2), LTHREE(3), DOME(4);

    private final int value;

    /**
     * @param value corrispondente alla costruzione
     */
    Block(int value) {
        this.value = value;
    }

    /**
     * @return il valore intero corrispondente alla costruzione
     */
    public int getValue() {
        return value;
    }
}
