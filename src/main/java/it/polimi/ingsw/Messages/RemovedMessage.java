package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Color;

public class RemovedMessage implements Message{

    private Color player;

    public RemovedMessage(Color player){
        this.player = player;
    }

    public Color getPlayer(){return player;}

}
