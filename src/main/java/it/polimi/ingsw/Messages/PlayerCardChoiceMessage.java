package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Player;

public class PlayerCardChoiceMessage implements Message {

    Player player;
    String card;

    public PlayerCardChoiceMessage(Player player, String card) {
        this.player = player;
        this.card = card;
    }

    public String getCard() {
        return card;
    }


    public Player getPlayer() {
        return player;
    }
}
