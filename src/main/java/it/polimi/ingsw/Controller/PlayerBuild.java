package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Worker;

public class PlayerBuild implements Message{

    Player player;
    Worker worker;
    int x,y;

    public PlayerBuild(Player player,Worker worker,int x,int y){
        this.player = player;
        this.worker = worker;
        this.x = x;
        this.y = y;
    }

    public Player getPlayer() {
        return player;
    }

    public Worker getWorker() {
        return worker;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}