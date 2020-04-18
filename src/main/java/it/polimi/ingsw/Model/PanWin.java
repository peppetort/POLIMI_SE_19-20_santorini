package it.polimi.ingsw.Model;

/**
 * Rappresenta la classe che modellizza la condizione di vittoria se il
 * giocatore possiede la {@link Card} PAN
 * <p>
 * Estende la classe {@link DefaultWin} poiché aggiunge una condizione di vittoria
 * a quella di default
 * </p>
 */
public class PanWin extends DefaultWin {

    /**
     * Rappresenta il costruttore della classe {@link PanWin}
     * <p>
     * Tramite i metodi di {@link Player} vengono settati gli attributi
     * {@link #board}, {@link #worker1} e {@link #worker2}.
     * </p>
     *
     * @param player giocatore che istanzia la classe
     */
    public PanWin(Player player) {
        super(player);
    }

    /**
     * Ritorna un booleano che indica se il giocatore ha vinto oppure no
     * <p>
     * Controlla inzialmente se il giocatore ha vinto, chiamando il metodo di
     * {@link DefaultWin}: se ritorna flase, controlla la condizione di vittoria di Pan.
     * Quindi per ciascua pedina, controlla che la differenza di livello tra la
     * vecchia {@link Box} e quella attuale è 2
     *
     * </p>
     *
     * @return true se il giocatore ha vinto, false altrimenti
     * @throws NullPointerException se l'attributo lastBox di {@link Worker} non è settato
     */
    @Override
    public boolean winChecker() {
        try {
            if (super.winChecker()) {
                return true;
            } else {

                Box w1LastBox = worker1.getLastBox();
                Box w1ActualBox = board.getBox(worker1.getXPos(), worker1.getYPos());
                Box w2LastBox = worker2.getLastBox();
                Box w2ActualBox = board.getBox(worker2.getXPos(), worker2.getYPos());

                if (w1LastBox == null && w2LastBox != null) {
                    return w2LastBox.getDifference(w2ActualBox) == 2;
                } else if (w1LastBox != null && w2LastBox == null) {
                    return w1LastBox.getDifference(w1ActualBox) == 2;
                } else if (w1LastBox != null) {
                    return w1LastBox.getDifference(w1ActualBox) == 2 || w2LastBox.getDifference(w2ActualBox) == 2;
                } else {
                    return false;
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
    }
}
