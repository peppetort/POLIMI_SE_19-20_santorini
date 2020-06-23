package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Color;

/**
 * message from server to client used to remove the {@link it.polimi.ingsw.Model.Worker }
 * of the {@link it.polimi.ingsw.Model.Player} that has lost
 *
 */

public class RemovedMessage implements Message{

    private Color player;

    public RemovedMessage(Color player){
        this.player = player;
    }

    public Color getPlayer(){return player;}

}
