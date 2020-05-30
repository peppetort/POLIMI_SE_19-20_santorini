package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class ChatUpdateMessage implements Message{

    String message;

    public ChatUpdateMessage(Player player,String text){
        message = player.getUsername() + ": "+text+"\n";
    }

    public String getMessage(){return message;}

}
