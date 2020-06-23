package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;

/**
 * message from server to client used for
 * update the god of the client chosen
 *
 */
public class CardUpdateMessage implements Message{
    private final God card;

    public CardUpdateMessage(God card){
        this.card = card;
    }

    public God getCard(){
        return this.card;
    }
}
