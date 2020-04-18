package it.polimi.ingsw.Model;

/**
 * Rappresenta l'interfaccai che specifica la
 * condizinoe di vittoria del {@link Player}.
 * <p>
 * Fa parte dello Strategy Pattern
 * </p>
 */
public interface Win {
    /**
     * Ritorna un boolean che indica se il giocatore che l'ha invocato
     * ha vinto oppure no.
     *
     * @return true se la partita è vinta, false altrimenti
     */
    boolean winChecker();
}
