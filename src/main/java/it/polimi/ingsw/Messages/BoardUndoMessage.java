package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Color;

public class BoardUndoMessage implements Message{

    private final Color player;



    private final Integer worker;
    private final int level;
    private final int x;
    private final int y;

    public BoardUndoMessage(Color player,Integer worker,int x, int y, int level){
        this.level = level;
        this.x = x;
        this.y = y;
        this.player=player;
        this.worker=worker;
    }

    public int getLevel(){
        return this.level;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    public Color getPlayer() {
        return player;
    }
    public Integer getWorker() {
        return worker;
    }

}
