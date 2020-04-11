package it.polimi.ingsw.View;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;

public abstract class View extends Observable<Message> implements Observer<Message> {

    Player player;
    Player opponent;
    protected abstract void showMessage(Object message);

    protected View(Player player,Player opponent){
        this.player = player;
        this.opponent = opponent;
    }

    //TODO: implementare le giuste notify
    public void handleDeck(String card1,String card2,String card3)
    {
        //notify(new DeckChoice())
    }
    public void handleCard(String card)
    {

       // notify(new CardChoice(player,));
    }
    public void handleSelection(int row,int column)
    {
       // notify(new PlayerStart());
    }
    public void handleInitialization() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(opponent);
        notify(new InitializePlayersMessage(players));

    }
    public void handleMove(int row,int column)
    {
        notify(new PlayerMove(player,row,column));
    }
    public void handleBuild(int row,int column)
    {
        notify(new PlayerBuild(player,row,column));
    }
    public void handlePlacing(Player player,int x1,int y1,int x2,int y2){ notify(new PlacePawn(player, x1, y1, x2, y2)); }
}