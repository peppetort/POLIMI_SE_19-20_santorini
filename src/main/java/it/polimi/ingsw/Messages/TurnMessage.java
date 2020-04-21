package it.polimi.ingsw.Messages;



import java.util.HashMap;

public class TurnMessage implements Message {

    HashMap<String, Boolean> turn;

    public TurnMessage(HashMap<String, Boolean> turn){
        this.turn = turn;
    }
    public HashMap<String, Boolean> getTurn(){
        return turn;
    }

//    String player;
//
//    public TurnMessage(String player) {
//        this.player = player;
//    }
//
//    public String getPlayer() {
//        return player;
//    }
}
