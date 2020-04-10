package it.polimi.ingsw.View;


import it.polimi.ingsw.Controller.Message;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

public abstract class View extends Observable<Message> implements Observer<Message> {

    Player player;
    protected abstract void showMessage(Object message);

    protected View(Player player){
        this.player = player;
    }
}