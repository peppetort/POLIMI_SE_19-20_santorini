package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;

public class CardUpdateMessage implements Message{
    private final God card;

    public CardUpdateMessage(God card){
        this.card = card;
    }

    public God getCard(){
        return this.card;
    }
}
