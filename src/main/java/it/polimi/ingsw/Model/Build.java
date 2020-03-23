package it.polimi.ingsw.Model;

/**
 * Rappresenta l'interfaccai che specifica la
 * l'azione di costruzione del {@link Player}.
 * <p>
 *     Fa parte dello Strategy Pattern
 * </p>
 */
public interface Build {

    /**
     * Sposta la pedina specificata nella posizione
     * specificata. A seconda della classe che lo implementa
     * controlla le condizioni apposite.
     *
     * @param worker è la pedina che costruisce
     * @param x è la posizione X della {@link Board} sulla quale si vuole costruire
     * @param y è la posizione Y della {@link Board} sulla quale si vuole costruire
     */
    void build(Worker worker, int x, int y);
}
