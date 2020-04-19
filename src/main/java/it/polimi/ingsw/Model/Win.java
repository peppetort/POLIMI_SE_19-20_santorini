package it.polimi.ingsw.Model;

/**
 * <p>
 * Strategy Pattern interface for the Win condition for the {@link Player}
 * </p>
 */
public interface Win {
    /**
     * @return true if the {@link Player} has won
     */
    boolean winChecker();
}
