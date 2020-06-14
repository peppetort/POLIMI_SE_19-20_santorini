package it.polimi.ingsw.Model;

/**
 * <p>
 * Strategy Pattern interface for the Turn
 * </p>
 */
public interface Turn {
    void start(Worker worker);
    void end();
    void move(int x, int y);
    void build(int x, int y);
    void buildDome(int x, int y);
    boolean won();
    void undo();
}
