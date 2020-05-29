package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

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
