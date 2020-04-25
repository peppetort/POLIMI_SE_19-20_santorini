package it.polimi.ingsw.Messages;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionsUpdateMessage implements Message{

    ArrayList<String> actions = new ArrayList<>();

    public void addAction(String action){
        actions.add(action);
    }

    public ArrayList<String> getActions(){
        return actions;
    }
}
