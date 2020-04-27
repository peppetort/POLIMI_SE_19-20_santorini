package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;

import java.util.ArrayList;

public class ClientStatus {
    private final String username;
    private String card;
    private final Color color;
    private String turn;
    private ArrayList<String> actions;
    private final ArrayList<String> messages = new ArrayList<>();
    private ArrayList<God> deck;

    private final String lengthMarker = "-";
    private final String widthMarker = "+";

    public ClientStatus(String username, Color color) {
        this.username = username;
        this.color = color;
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
            print();
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

        print();
    }

    public synchronized void lose(String username){
        if(username.equals(this.username)){
            this.actions = null;
            messages.clear();
            messages.add("YOU LOSE :(");
            print();
        }else {
            System.out.print(username + " lost");
        }

    }

    public synchronized void updateAction(ArrayList<String> actions){
        this.actions = actions;

        print();

        if(actions.get(0).equals("deck")){
            printAllCards();
        }else if(actions.get(0).equals("card")){
            printDeck();
        }
    }

    private synchronized void printActions(int length) {

        String actionsLabel = "ACTION";
        String bodyFormat;

        int actionsLength = (length - actionsLabel.length()) / 2 - 1;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < actionsLength; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + actionsLabel + " ");
        for (int i = 0; i < actionsLength; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        for (String act: actions){
            bodyFormat = "%-3s%s%" + (length + 1 - act.length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, "> "+act.toUpperCase(), widthMarker);
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);

    }

    private synchronized void printMessages(int length){
        String messagesLabel = "MESSAGES";
        String bodyFormat;

        int messagesLength = (length - messagesLabel.length()) / 2 - 1;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < messagesLength; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + messagesLabel + " ");
        for (int i = 0; i < messagesLength; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        for(String message: messages){
            bodyFormat = "%-3s%s%" + (length + 3 - message.length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, message, widthMarker);
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);
    }

    public synchronized void printAllCards(){
        String title = "ALL CARDS";
        String menuFormat;
        String bodyFormat;

        int maxLength = 8;

        menuFormat = "%-";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%n";

        int length = maxLength / 2 + maxLength / 2 + title.length() + 6;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(menuFormat, widthMarker, title, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        for(God g: God.values()){
            String name = g.name();
            bodyFormat = "%-3s%s%" + (length + 3 - name.length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, name, widthMarker);
        }

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
    }

    public synchronized void printDeck(){
        String title = "AVAILABLE CARDS";
        String menuFormat;
        String bodyFormat;

        int maxLength = 8;

        menuFormat = "%-";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%n";

        int length = maxLength / 2 + maxLength / 2 + title.length() + 6;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(menuFormat, widthMarker, title, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        for(God name: deck){
            bodyFormat = "%-3s%s%" + (length + 3 - name.name().length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, name, widthMarker);
        }

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
    }

    public synchronized void print(){
        String title = "STATUS";
        String menuFormat;
        String bodyFormat;

        StringBuilder usernameLabel = new StringBuilder("Username: ").append(this.username);
        StringBuilder colorLabel = new StringBuilder("Color: ").append(this.color);

        StringBuilder cardLabel = null;
        if (this.card != null) {
            cardLabel = new StringBuilder("Card: ").append(this.card);
        }

        StringBuilder turnLabel = null;

        if (turn != null) {
            turnLabel = new StringBuilder("Turn: ").append(this.turn);
        }


        int maxLength = title.length();

        if (usernameLabel.length() > maxLength) {
            maxLength = usernameLabel.length();
        }
        if (colorLabel.length() > maxLength) {
            maxLength = colorLabel.length();
        }
        if (turnLabel != null && turnLabel.length() > maxLength) {
            maxLength = turnLabel.length();
        }
        if (cardLabel != null && cardLabel.length() > maxLength) {
            maxLength = cardLabel.length();
        }

        if(actions != null) {
            for (String act : actions) {
                if (act.length() > maxLength) {
                    maxLength = act.length();
                }
            }
        }

        menuFormat = "%-";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(5 + maxLength / 2);
        menuFormat += "s%n";

        int length = maxLength / 2 + maxLength / 2 + title.length() + 6;

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        System.out.printf(menuFormat, widthMarker, title, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");

        bodyFormat = "%-3s%s%" + (maxLength / 2 + maxLength / 2 + 7 + title.length() - usernameLabel.length()) + "s%n";
        System.out.printf(bodyFormat, widthMarker, usernameLabel, widthMarker);
        bodyFormat = "%-3s%s%" + (maxLength / 2 + maxLength / 2 + 7 + title.length() - colorLabel.length()) + "s%n";
        System.out.printf(bodyFormat, widthMarker, colorLabel, widthMarker);

        if (cardLabel != null) {
            bodyFormat = "%-3s%s%" + (maxLength / 2 + maxLength / 2 + 7 + title.length() - cardLabel.length()) + "s%n";
            System.out.printf(bodyFormat, widthMarker, cardLabel, widthMarker);
        }

        if (turnLabel != null) {
            bodyFormat = "%-3s%s%" + (maxLength / 2 + maxLength / 2 + 7 + title.length() - turnLabel.length()) + "s%n";
            System.out.printf(bodyFormat, widthMarker, turnLabel, widthMarker);
        }
        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);

        if(turn != null && turn.equals(username) && actions!=null) {
            printActions(length);
        }

        if(messages.size() != 0) {
            printMessages(length);
        }

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
        System.out.print("\n");


    }
}
