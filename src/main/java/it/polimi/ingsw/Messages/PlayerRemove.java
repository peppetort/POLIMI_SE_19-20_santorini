package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerRemove implements Message {
    private Player player;

    public Player getPlayer() {
        return player;
    }
}
