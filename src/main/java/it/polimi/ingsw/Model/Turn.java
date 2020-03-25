package it.polimi.ingsw.Model;

public interface Turn {
    void start();
    void end();
    void move(Worker worker, int x, int y);
    void build(Worker worker, int x, int y);
    boolean won();
}
