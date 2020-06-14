package it.polimi.ingsw.Model;

/**
 * All possible actions that a player can perform
 */
public enum Actions {
    DECK ("DECK"),
    CARD ("CARD"),
    UNDO ("UNDO"),
    SELECT ("SELECT"),
    PLACE ("PLACE"),
    MOVE ("MOVE"),
    BUILD ("BUILD"),
    END ("END"),
    DOME ("DOME");

    public final String value;

    Actions(String label) {
        this.value = label;
    }
}
