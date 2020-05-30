package it.polimi.ingsw.Model;

public enum Actions {
    DECK ("DECK"),
    CARD ("CARD"),
    UNDO ("UNDO"),
    SELECT ("SELECT"),
    PLACE ("PLACE"),
    MOVE ("MOVE"),
    BUILD ("BUILD"),
    END ("END"),
    BUILD_DOME ("BUILD_DOME");

    public final String value;

    private Actions(String label) {
        this.value = label;
    }
}
