package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerRemove implements Message {
    private Player player;
    public PlayerRemove(Player player){this.player = player;}
    public Player getPlayer() {
        return player;
    }
}
