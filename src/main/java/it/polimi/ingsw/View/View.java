package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;


public abstract class View extends Observable<Message> implements Observer<Message>{

    Player player;

    protected View(Player player) {
        this.player = player;
    }

    public void handleDeck(Message message) {
        ((PlayerDeckMessage)message).setPlayer(player);
        notify(message);
    }

    public void handleCardChoice(Message message) {
        ((PlayerCardChoiceMessage)message).setPlayer(player);
        notify(message);
    }

    public void handleSelect(Message message){
        ((PlayerSelectMessage)message).setPlayer(player);
        switch(((PlayerSelectMessage)message).getNum()){
            case 1:
                ((PlayerSelectMessage)message).setWorker(player.getWorker1());
                break;
            case 2:
                ((PlayerSelectMessage)message).setWorker(player.getWorker2());
                break;
                //TODO : devo lanciare un'eccezione in caso che io abbia selezionato piu di 3 player
        }
        notify(message);
    }

    public void handleMove(Message message) {
        ((PlayerMoveMessage)message).setPlayer(player);
        notify(message);
    }

    public void handleBuild(Message message) {
        ((PlayerBuildMessage)message).setPlayer(player);
        notify(message);
    }

    public void handleBuildDome(Message message) {
        ((PlayerBuildDomeMessage)message).setPlayer(player);
        notify(message);
    }

    public void handlePlacing(Message message) {
        ((PlayerPlacePawnsMessage)message).setPlayer(player);
        notify(message);
    }
    public void handleUndo(Message message){
        ((PlayerUndoMessage)message).setPlayer(player);
        notify(message);
    }

    public void handleEnd(Message message) {
        ((PlayerEndMessage)message).setPlayer(player);
        notify(message);
    }

}