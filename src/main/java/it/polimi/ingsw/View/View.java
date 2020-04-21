package it.polimi.ingsw.View;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class View extends Observable<Message> implements Observer<Message> {

    Player player;

    protected abstract void showMessage(Object message);

    protected View(Player player) {
        this.player = player;
    }

    public void handleDeck(String card1, String card2, String card3) {
        Set<String> cardsNames = new HashSet<>();
        cardsNames.add(card1);
        cardsNames.add(card2);
        cardsNames.add(card3);
        notify(new DeckChoice(player, cardsNames));
    }

    public void handleDeck(String card1, String card2) {
        Set<String> cardsNames = new HashSet<>();
        cardsNames.add(card1);
        cardsNames.add(card2);
        notify(new DeckChoice(player, cardsNames));
    }

    public void handleCard(String card) {
        notify(new CardChoice(player, card));
    }

    public void handleStart(int w) {
        if (w == 1) {
            notify(new PlayerStart(player, player.getWorker1()));
        } else if (w == 2) {
            notify(new PlayerStart(player, player.getWorker2()));
        }
    }

    public void handleMove(int row, int column) {
        System.out.println(row);
        System.out.println(column);
        notify(new PlayerMove(player, row, column));
    }

    public void handleBuild(int row, int column) {
        notify(new PlayerBuild(player, row, column));
    }

    public void handlePlacing(int x1, int y1, int x2, int y2) {
        notify(new PlacePawn(player, x1, y1, x2, y2));
    }

    public void handleEnd() {
        notify(new PlayerEnd(player));
    }

}