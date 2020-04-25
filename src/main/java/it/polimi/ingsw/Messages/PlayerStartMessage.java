package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Worker;

public class PlayerStartMessage implements Message {
    Player player;
    Worker worker;
    public PlayerStartMessage(Player player, Worker worker) {
        this.player = player;
        this.worker = worker;
    }

    public Player getPlayer() {
        return player;
    }
    public Worker getWorker() {
        return worker;
    }
}
