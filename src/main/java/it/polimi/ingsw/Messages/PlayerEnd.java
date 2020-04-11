package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerEnd implements Message {
    Player player;
    public PlayerEnd(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
