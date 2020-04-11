package it.polimi.ingsw.View;


import it.polimi.ingsw.Controller.*;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

public abstract class View extends Observable<Message> implements Observer<Message> {

    Player player;
    protected abstract void showMessage(Object message);

    protected View(Player player){
        this.player = player;
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
    public void handleMove(int row,int column)
    {
        notify(new PlayerMove(player,row,column));
    }
    public void handleBuild(int row,int column)
    {
        notify(new PlayerBuild(player,row,column));
    }
}