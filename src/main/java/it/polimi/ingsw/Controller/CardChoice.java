package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Player;

public class CardChoice implements Message {

    Player player;
    Card card;

    public CardChoice(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
