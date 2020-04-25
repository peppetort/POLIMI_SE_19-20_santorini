package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerRemoveMessage implements Message {
    private String player;

    public PlayerRemoveMessage(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
