package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Color;

public class BoardUpdatePlaceMessage implements Message{
    private final Color player;
    private final int worker;
    private final int x;
    private final int y;

    public BoardUpdatePlaceMessage(Color player, int worker, int x, int y){
        this.player = player;
        this.worker = worker;
        this.x = x;
        this.y = y;
    }

    public Color getPlayer(){
        return player;
    }

    public int getWorker(){
        return worker;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}

