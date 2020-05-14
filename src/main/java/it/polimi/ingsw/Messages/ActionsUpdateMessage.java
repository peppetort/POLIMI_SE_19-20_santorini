package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Actions;

import java.util.ArrayList;

public class ActionsUpdateMessage implements Message{

    ArrayList<Actions> actions = new ArrayList<>();

    public void addAction(Actions action){
        actions.add(action);
    }

    public ArrayList<Actions> getActions(){
        return actions;
    }
}
