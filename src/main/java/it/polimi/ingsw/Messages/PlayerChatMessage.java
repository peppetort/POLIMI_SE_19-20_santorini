package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;

/**
 * message from client to server used to send a message in the chat
 *
 */

public class PlayerChatMessage implements Message{

    String message;
    Player player;

    public PlayerChatMessage(String message){
        this.message = message;
    }

    public void setPlayer(Player player){this.player = player;}

    public String getMessage(){return message;}

    public Player getPlayer(){return player;}

}
