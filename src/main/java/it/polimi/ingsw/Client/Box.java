package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Color;

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

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPlayer(Color player, int worker) {
        this.player = player;
        this.worker = worker;
    }

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