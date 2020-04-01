package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.PlayerLostException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;

import java.util.*;

public class Controller implements Observer<Message>{
    private HashMap<Player,Boolean> turn = new HashMap<>();
    private HashMap<Player,Boolean> outcome = new HashMap<>();
    private HashMap<Player,Player> players = new HashMap<>(); //La chiave è il riferimento del "client",il value è il riferimento del model
    private ArrayList<Card> cards = new ArrayList<>();
    private boolean firstTurn;
    private Game game;
    private int loosingPlayers;

    public HashMap<Player, Boolean> getTurn() {
        return turn;
    }

    public HashMap<Player, Boolean> getOutcome() {
        return outcome;
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public Game getGame() {
        return game;
    }

    public int getLoosingPlayers() {
        return loosingPlayers;
    }

    public Controller(Game game) {
        this.game = game;
        this.cards = game.getCards();
        for(Player p: game.getPlayers()) {
            turn.put(p,false);
            outcome.put(p,null);
        }
        firstTurn = true;
        turn.replace(game.getPlayers().get(0),true);
        loosingPlayers = 0;
    }

    /**
     * Quando chiamato il turno passa al giocatore successivo
     */
    private void updateTurn() {
        int index;
        for(Player p:game.getPlayers()) {
            if(turn.get(p)) {
                turn.replace(p,false);
                index = (game.getPlayers().indexOf(p)+1)%(game.getPlayers().size());
                if(index == 0 && firstTurn){
                    firstTurn = false;
                }
                turn.replace(game.getPlayers().get(index),true);
                break;
            }
        }
    }

    private void performMove(PlayerMove message){
        Player p = players.get(message.getPlayer());
        if(turn.get(p) && !firstTurn && outcome.get(p) == null){
            try {
                p.getTurn().move( message.getX(), message.getY());
                if(p.getTurn().won()){
                    outcome.replace(p,true);
                    for(Player c:game.getPlayers()){
                        if(!c.equals(p)){
                            outcome.replace(c,false);
                        }
                    }
                }
            }catch(RuntimeException e){
                System.err.println(e.getMessage());
            }
        }else{
            throw new RuntimeException("Not your turn buddy!");
        }
    }
    private void performBuild(PlayerBuild message){
        Player p = players.get(message.getPlayer());
        int index = game.getPlayers().indexOf(p);
        if(turn.get(p) && !firstTurn && outcome.get(p) == null){
            try {
                p.getTurn().build(message.getX(),message.getY());
            }catch (RuntimeException e){
                System.err.println(e.getMessage());
            }
        }else{
            throw new RuntimeException("Not your turn buddy!");
        }
    }

    private void performChoice(CardChoice message){
        Player p = players.get(message.getPlayer()); // da riferimento nullo
        Card card = new Card(message.getCard().getName());
        if(firstTurn && turn.get(p) && outcome.get(p) == null){
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

    private void performDeckBuilding(DeckChoice message){
        Player p = players.get(message.getPlayer());
        if(turn.get(p) && firstTurn &&  outcome.get(p)==null){
            for(Card c: message.getCards()){
                game.addCard(c);
                cards.add(c);
            }
            updateTurn();
        }
        else {
            throw new RuntimeException("Non è il tuo turno");
        }
    }
    //TODO: risolvere problema del worker
    private void performStart(PlayerStart message){
        Player p = players.get(message.getPlayer());
        Worker w;
        if(message.getWorker().equals(message.getPlayer().getWorker1())) {
            w = p.getWorker1();
        }
        else{
            w = p.getWorker2();
        }

        if(turn.get(p) && outcome.get(p) == null){
            try{
                game.getPlayers().get(game.getPlayers().indexOf(p)).getTurn().start(w);
            }catch (RuntimeException e){
                if(e instanceof PlayerLostException){
                    outcome.replace(p,false);
                    loosingPlayers ++;
                    if(loosingPlayers == game.getPlayers().size()-1){
                        for(Player c : game.getPlayers()){
                            if(outcome.get(c) == null){
                                outcome.replace(c,true);
                            }
                        }
                    }
                }
                else {
                    System.err.println(e.getMessage());
                }
            }
        }else{
            throw new RuntimeException("Non è il tuo turno");
        }
    }

    private void performEnd(PlayerEnd message){
        Player p = players.get(message.getPlayer());
        if(turn.get(p) && outcome.get(p) == null){
            try{
                //game.getPlayers().get(game.getPlayers().indexOf(p)).getTurn().end();
                p.getTurn().end();
                updateTurn();
            }catch (RuntimeException e){
                System.err.println(e.getMessage());
            }
        }else{
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

    public ArrayList<Card> getCards() {
        return cards;
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
        if(message instanceof CardChoice){
            performChoice((CardChoice) message);
        }
        if(message instanceof DeckChoice){
            performDeckBuilding((DeckChoice) message);
        }
        if(message instanceof PlayerStart){
            performStart((PlayerStart) message);
        }
        if(message instanceof PlayerEnd){
            performEnd((PlayerEnd) message);
        }
    }

    public void initializePlayers(InitializePlayersMessage message) {
        try{
            for(Player p: message.getPlayers()){
                players.put(p,game.getPlayers().get(message.getPlayers().indexOf(p)));
            }
        }catch(NullPointerException e){
            System.err.println(e.getMessage());
        }
    }



}
