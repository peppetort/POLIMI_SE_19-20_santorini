package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

/**
 *
 * message used to remove a {@link Player} from the {@link it.polimi.ingsw.Server.Session}
*/

public class PlayerRemoveMessage implements Message {
    private String player;

    public PlayerRemoveMessage(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
