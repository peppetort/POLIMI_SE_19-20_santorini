package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerUndoMessage implements Message {
    Player player;
    public PlayerUndoMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}