package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerRemove implements Message {
    private String player;

    public PlayerRemove(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
