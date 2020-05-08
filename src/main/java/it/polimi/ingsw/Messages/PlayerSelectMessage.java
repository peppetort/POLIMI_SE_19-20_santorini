package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Worker;

public class PlayerStartMessage implements Message {
    private Player player;
    private Worker worker;
    private int num;

    public PlayerStartMessage(int num){
        this.num = num;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
