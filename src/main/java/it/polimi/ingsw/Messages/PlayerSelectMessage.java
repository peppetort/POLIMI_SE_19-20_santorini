package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Worker;

public class PlayerSelectMessage implements Message {
    private Player player;
    private Worker worker;
    private int num;

    public PlayerSelectMessage(int num){
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

}
