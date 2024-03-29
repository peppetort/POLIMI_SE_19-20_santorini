package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;


/**
 * message from client to server used to chose a {@link God}
 *
 */

public class PlayerCardChoiceMessage implements Message {

    private God card;
    private Player player;

    public PlayerCardChoiceMessage(God card) {
        this.card = card;
    }

    public PlayerCardChoiceMessage(Player player,God card) {
        this.card = card;
        this.player = player;
    }

    public God getCard() {
        return card;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
