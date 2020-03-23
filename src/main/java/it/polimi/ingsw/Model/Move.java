package it.polimi.ingsw.Model;

/**
 * Rappresenta l'interfaccai che specifica la
 * la mossa del {@link Player}.
 * <p>
 *     Fa parte dello Strategy Pattern
 * </p>
 */
public interface Move {

    /**
     * Sposta la pedina specificata nella posizione
     * specificata. A seconda della classe che lo implementa
     * controlla le condizioni apposite.
     *
     * @param worker è la pedina da muovere
     * @param x è la posizione X della {@link Board} sulla quale si vuole posizionare la pedina
     * @param y è la posizione Y della {@link Board} sulla quale si vuole posizionare la pedina
     */
    void move(Worker worker, int x, int y);
}
