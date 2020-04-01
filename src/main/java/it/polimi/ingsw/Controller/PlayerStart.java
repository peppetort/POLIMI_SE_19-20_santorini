package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Worker;

public class PlayerStart implements Message {
    Player player;
    Worker worker;
    public PlayerStart(Player player, Worker worker) {
        this.player = player;
        this.worker = worker;
    }
    @Override
    public Player getPlayer() {
        return player;
    }
    public Worker getWorker() {
        return worker;
    }
}
