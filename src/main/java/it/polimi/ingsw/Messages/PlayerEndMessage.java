package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;

/**
 * message from client to server used to terminate its own turn
 *
 */

public class PlayerEndMessage implements Message {
    private Player player;
    public PlayerEndMessage(){
    }
    public PlayerEndMessage(Player player){
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
