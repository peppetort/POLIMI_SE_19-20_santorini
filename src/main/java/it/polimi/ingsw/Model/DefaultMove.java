package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.AthenaGoUpException;
import it.polimi.ingsw.Exceptions.InvalidMoveException;

/**
 * Rappresenta la classe utilizzata dal {@link Player} nel caso in cui non possegga una carta in grado di modificare
 * la sua mossa di movimento.
 */
public class DefaultMove implements Move {

    final Board board;

    /**
     * Rappresenta il metodo costruttore.
     *
     * @param player rappresenta il {@link Player} che si muoverà secondo le regole base del gioco.
     */
    public DefaultMove(Player player) {
        this.board = player.getSession().getBoard();
    }

    /**
     * Rappresenta il metodo per effettuare un movimento secondo le regole base del gioco.
     *
     * @param worker è la pedina da muovere
     * @param x      è la posizione X della {@link Board} sulla quale si vuole posizionare la pedina
     * @param y      è la posizione Y della {@link Board} sulla quale si vuole posizionare la pedina
     * @throws RuntimeException          se le coordinate non sono valide (non è una cella adiacente oppure è la stessa
     *                                   delle coordinate attuali del {@link Worker})
     * @throws RuntimeException          se la cella su cui voglio muovermi è occupata da un altro {@link Worker}
     * @throws RuntimeException          se per muovermi su quella cella devo compiere un salto di due o più livelli
     * @throws IndexOutOfBoundsException se voglio muovermi fuori dai bordi di {@link Board}
     * @throws NullPointerException      se il riferimento a {@link Worker} è null
     */
    @Override
    public void move(Worker worker, int x, int y) throws IndexOutOfBoundsException, NullPointerException {

        int wX = worker.getXPos();
        int wY = worker.getYPos();

        Box workerBox = board.getBox(wX, wY);
        Box nextBox = board.getBox(x, y);

        if (x > wX + 1 || x < wX - 1 || y > wY + 1 || y < wY - 1 || (x == wX && y == wY)) {
            throw new InvalidMoveException("Move too far from the worker position!");
        } else if (!nextBox.isFree()) {
            throw new InvalidMoveException("Box already occupied!");
        } else if (!workerBox.compare(nextBox)) {
            throw new InvalidMoveException("Level in box " + x + " " + y + "is too high!");
        } else {
            board.placePawn(worker, x, y);
            workerBox.removePawn();  //rimuovo pedina dalla vecchia pos
            worker.updateLastBox(workerBox); // aggiorno l'ultima box nel worker
        }
    }

    @Override
    public void moveNoGoUp(Worker worker, int x, int y) throws IndexOutOfBoundsException, NullPointerException{
        int wX = worker.getXPos();
        int wY = worker.getYPos();

        Box workerBox = board.getBox(wX, wY);
        Box nextBox = board.getBox(x, y);

        if (workerBox.getDifference(nextBox) < 0) {
            throw new AthenaGoUpException("Can't go up! Opponent used Athena.");
        }

        move(worker, x, y);
    }

}
