package it.polimi.ingsw.View;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.HashSet;
import java.util.Set;

public abstract class View extends Observable<Message> implements Observer<Message> {

    Player player;

    protected View(Player player) {
        this.player = player;
    }

    public void handleDeck(String card1, String card2, String card3) {
        Set<String> cardsNames = new HashSet<>();
        cardsNames.add(card1);
        cardsNames.add(card2);
        cardsNames.add(card3);
        notify(new PlayerDeckMessage(player, cardsNames));
    }

    public void handleDeck(String card1, String card2) {
        Set<String> cardsNames = new HashSet<>();
        cardsNames.add(card1);
        cardsNames.add(card2);
        notify(new PlayerDeckMessage(player, cardsNames));
    }

    public void handleCard(String card) {
        notify(new PlayerCardChoiceMessage(player, card));
    }

    public void handleStart(int w) {
        if (w == 1) {
            notify(new PlayerStartMessage(player, player.getWorker1()));
        } else if (w == 2) {
            notify(new PlayerStartMessage(player, player.getWorker2()));
        }
    }

    public void handleMove(int row, int column) {
        notify(new PlayerMoveMessage(player, row, column));
    }

    public void handleBuild(int row, int column) {
        notify(new PlayerBuildMessage(player, row, column));
    }

    public void handleBuildDome(int row, int column) {
        notify(new PlayerBuildDomeMessage(player, row, column));
    }

    public void handlePlacing(int x1, int y1, int x2, int y2) {
        notify(new PlayerPlacePawnsMessage(player, x1, y1, x2, y2));
    }
    public void handleUndo(){notify(new PlayerUndoMessage(player));}
    public void handleEnd() {
        notify(new PlayerEndMessage(player));
    }

}