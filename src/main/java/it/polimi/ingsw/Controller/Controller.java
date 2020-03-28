package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;

import java.util.*;

public class Controller implements Observer<Message>{
    public HashMap<Player,Boolean> turn = new HashMap<>();
    private HashMap<Player,Boolean> outcome = new HashMap<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private boolean firstTurn;

    private Game game;

    public Controller(Game game) {
        this.game = game;
        this.cards = game.getCards();
        for(Player p: game.getPlayers()) {
            turn.put(p,false);
            outcome.put(p,null);
        }
        firstTurn = true;
        turn.replace(game.getPlayers().get(0),true);
    }

    /**
     * Quando chiamato il turno passa al giocatore successivo
     */
    public void updateTurn() {
        int index;
        for(Player p:game.getPlayers()) {
            if(turn.get(p)) {
                turn.replace(p,false);
                game.getPlayers().get(game.getPlayers().indexOf(p)).getTurn().end();
                index = (game.getPlayers().indexOf(p)+1)%(game.getPlayers().size());
                if(index == 0 && firstTurn){
                    firstTurn = false;
                }
                turn.replace(game.getPlayers().get(index),true);
                if(!firstTurn){
                    //try
                    //game.getPlayers().get(index).getTurn().start();
                    // controllo se tutti i player hanno perso
                }
                break;
            }
        }
    }

    private void performMove(PlayerMove message){
        Player p = message.getPlayer();
        int index = game.getPlayers().indexOf(p);
        if(turn.get(p) && !firstTurn){
            game.getPlayers().get(index).getTurn().move(message.getWorker(),message.getX(),message.getY());
            //getTurn().won() --> controllo vittoria
        }else{
            throw new RuntimeException("Not your turn buddy!");
        }
    }
    private void performBuild(PlayerBuild message){
        Player p = message.getPlayer();
        int index = game.getPlayers().indexOf(p);
        if(turn.get(p) && !firstTurn){
            game.getPlayers().get(index).getTurn().build(message.getWorker(),message.getX(),message.getY());
            updateTurn();
        }else{
            throw new RuntimeException("Not your turn buddy!");
        }
    }

    private void performChoose(CardChoose message){
        Player p = message.getPlayer(); // da riferimento nullo
        Card card = message.getCard();
        if(firstTurn && turn.get(p)){
            p.setCard(card);
            removeCard(card);
            if(p.equals(game.getPlayers().get(game.getPlayers().size()-1))){
                game.getPlayers().get(0).setCard(cards.get(0));
            }
            updateTurn();
        }
        else{
            throw new RuntimeException("Can't do that");
        }
    }

    private void performDeckBuilding(DeckChoose message){
        Player p = message.getPlayer();
        if(turn.get(p) && firstTurn ){
            for(Card c: message.getCards()){
                game.addCard(c);
            }
            updateTurn();
        }
        else {
            throw new RuntimeException("Non è il tuo turno");
        }
    }




    public boolean getFirstTurn(){
        return firstTurn;
    }

    private void removeCard(Card card){
        for(Card c: cards){
            if(c.getName() == card.getName()){
                cards.remove(c);
            }
        }
    }
    
    //message è la mossa generica
    @Override
    public void update(Message message) {
        if(message instanceof PlayerMove){
            performMove((PlayerMove) message);
        }
        if(message instanceof PlayerBuild){
            performBuild((PlayerBuild) message);
        }
        if(message instanceof CardChoose){
            performChoose((CardChoose) message);
        }
        if(message instanceof DeckChoose){
            performDeckBuilding((DeckChoose) message);
        }
    }

    //TODO : performstart/performend
    //TODO : getWin/getLoose
    //TODO : controllare che ogni message.getPlayer() è ancora in gioco
    //TODO : forall metodo getTurn try catch
}
