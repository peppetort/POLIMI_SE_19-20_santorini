package it.polimi.ingsw.Model;

/**
 * A {@link Card} encapsulate a {@link God}
 */

public class Card {
    private final God name;

    public Card(God god) {
        name = god;
    }

    public God getName() {
        return name;
    }
}
