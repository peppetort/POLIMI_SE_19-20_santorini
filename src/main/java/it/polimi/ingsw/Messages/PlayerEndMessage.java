package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerEndMessage implements Message {
    Player player;
    public PlayerEndMessage(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
