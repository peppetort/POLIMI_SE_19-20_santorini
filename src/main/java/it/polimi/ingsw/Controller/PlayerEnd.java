package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Player;

public class PlayerEnd implements Message {
    Player player;
    @Override
    public Player getPlayer() {
        return player;
    }
}
