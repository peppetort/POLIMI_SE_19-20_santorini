package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;

public class PlayerRemove implements Message {
    private Player player;
    @Override
    public Player getPlayer() {
        return player;
    }
}
