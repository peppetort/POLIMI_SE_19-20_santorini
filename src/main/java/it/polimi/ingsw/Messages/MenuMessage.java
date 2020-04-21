package it.polimi.ingsw.Messages;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuMessage implements Message {

    ArrayList<String> actions = new ArrayList<>();

    public MenuMessage(HashMap<String,Boolean> playerMenu){
        for(String s: playerMenu.keySet()){
            if(playerMenu.get(s)){
                actions.add(s);
            }
        }
    }

    public ArrayList<String> getActions(){
        return actions;
    }

}
