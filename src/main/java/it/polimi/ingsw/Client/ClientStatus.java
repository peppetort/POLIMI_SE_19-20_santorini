package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observable;

import java.awt.*;
import java.util.ArrayList;

public class ClientStatus extends Observable {
    private final String username;
    private String card;
    private final Color color;
    private String turn;
    private ArrayList<Actions> actions;
    private final ArrayList<String> messages = new ArrayList<>();
    private ArrayList<God> deck;



    public ClientStatus(String username, Color color) {
        this.username = username;
        this.color = color;
    }

    public String getCard() {
        return card;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Actions> getActions() {
        return actions;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public ArrayList<God> getDeck() {
        return deck;
    }


    public synchronized String getTurn(){
        return this.turn;
    }

    public synchronized String getUsername(){
        return this.username;
    }

    public synchronized void setCard(God card) {
        if(card != null) {
            this.card = card.toString();
            deck = null;
        }
    }

    public synchronized void updateDeck(ArrayList<God> deck){
        this.deck = deck;
    }

    public synchronized void updateTurn(String player){
        this.turn = player;

        if(!turn.equals(username)){
            messages.clear();
            messages.add("Wait your turn");
            //print();
            notify(2);
        }else {
            messages.clear();
        }
    }

    public synchronized void setWinner(String username){
        this.turn = null;
        this.actions = null;

        messages.clear();
        if(username.equals(this.username)){
            messages.add("YOU WIN :)");
        }else {
            messages.add("YOU LOSE :(");
        }
        notify(2);
       // print();
    }

    public synchronized void lose(String username){
        if(username.equals(this.username)){
            this.actions = null;
            messages.clear();
            messages.add("YOU LOSE :(");
            //print();
            notify(2);
        }else {
            System.out.print(username + " lost");
        }

    }

    public synchronized void updateAction(ArrayList<Actions> actions){
        this.actions = actions;

        //print();

        if(actions.get(0).equals(Actions.DECK)){
          //  printAllCards();
            notify(3);
        }else if(actions.get(0).equals(Actions.CARD)){
          //  printDeck();
            notify(4);
        }
        notify(2);
    }

    //TODO: DISCUTERE UNIVOCITA' DEI NOMI E GESTIONE
    public boolean myTurn(){
        return turn.equals(username);
    }

}
