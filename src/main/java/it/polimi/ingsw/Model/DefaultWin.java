package it.polimi.ingsw.Model;

/**
 * Rappresenta la classe che modellizza la condizione di vittoria di default
 */
public class DefaultWin implements Win {

    private Board board;
    private Worker worker1;
    private Worker worker2;

    /**
     * Rappresenta il costruttore della classe {@link DefaultWin}
     * <p>
     * Tramite i metodi di {@link Player} vengono settati gli attributi
     * {@link #board}, {@link #worker1} e {@link #worker2}.
     * </p>
     *
     * @param player giocatore che istanzia la classe
     */
    public DefaultWin(Player player) {
        this.board = player.getSession().getBoard();
        this.worker1 = player.getWorker1();
        this.worker2 = player.getWorker2();
    }

    /**
     * Ritorna un booleano che indica se il giocatore ha vinto oppure no.
     * <p>
     *     Tramite i metodi di {@link Worker} vengono presi le posizioni delle due
     *     pedine e si controlla se sulla {@link Box} su cui uno dei due si trova è
     *     costruito un {@link Block} di livello 3
     * </p>
     *
     * @return true se il giocatore ha vinto, false altrimenti
     */
    @Override
    public boolean winChecker() {

        try {
            int w1X = worker1.getXPos();
            int w1Y = worker1.getYPos();
            int w2X = worker2.getXPos();
            int w2Y = worker2.getYPos();

            return board.getBox(w1X, w1Y).getBlock() == Block.LTHREE || board.getBox(w2X, w2Y).getBlock() == Block.LTHREE;

        } catch (NullPointerException e) {
            return false;
        }
    }
}
