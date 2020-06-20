package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Color;

/**
 * Represents the cell of the board
 */
public class Box {
    private int level;
    private Color player;
    private Integer worker;
    private final int x;
    private final int y;

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
        level = 0;
        player = null;
        worker = null;
    }

    /**
     * Set building level
     *
     * @param level building level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Place specified pawn with specified color
     *
     * @param player color of the player who want to place
     * @param worker number of the pawn
     */
    public void setPlayer(Color player, int worker) {
        this.player = player;
        this.worker = worker;
    }

    /**
     * Remove pawn from box
     */
    public void clear() {
        this.player = null;
        this.worker = null;
    }

    public int getLevel() {
        return level;
    }

    public Color getPlayer() {
        return player;
    }

    public Integer getWorker() {
        return worker;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}