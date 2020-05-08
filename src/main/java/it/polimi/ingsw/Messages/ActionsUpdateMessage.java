package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Client.Actions;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionsUpdateMessage implements Message{

    ArrayList<Actions> actions = new ArrayList<>();

    public void addAction(Actions action){
        actions.add(action);
    }

    public ArrayList<Actions> getActions(){
        return actions;
    }
}
