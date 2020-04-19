package it.polimi.ingsw.Model;

/**
 * <p>
 * Strategy Pattern interface for the Move
 * </p>
 */
public interface Move {

    /**
     * @param worker selected worker
     * @param x      x coordinate where the {@link Player} wants to move
     * @param y      y coordinate where the {@link Player} wants to move
     */
    void move(Worker worker, int x, int y);

    void moveNoGoUp(Worker worker, int x, int y);
}
