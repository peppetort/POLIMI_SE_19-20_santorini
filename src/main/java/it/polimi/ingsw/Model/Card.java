package it.polimi.ingsw.Model;

/**
 * Rappresenta la classe contenitore del tipo God
 */

public class Card {
    private final God name;

    /**
     * Rappresenta il costruttore della classe {@link Card}
     *
     * @param god Divinità appartenente all'enumerazione God
     */
    public Card(God god) {
        name = god;
    }

    /**
     * @return nome della divinità rappresentata dalla carta (tipo God)
     */
    public God getName() {
        return name;
    }
}
