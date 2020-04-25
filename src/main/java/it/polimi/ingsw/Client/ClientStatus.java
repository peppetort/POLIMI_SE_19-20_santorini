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
        if(card == null) {
            this.card = card.toString();
        }
    }

    public synchronized void updateTurn(String player){
        this.turn = player;
        if(!turn.equals(username)){
            print();
        }
    }

    public synchronized void updateAction(ArrayList<String> actions){
        this.actions = actions;
        if(turn != null && turn.equals(username)){
            print();
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

    private synchronized void printMessages(int length, ArrayList<String> messages){
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

    public synchronized void print(){
        String title = "STATUS";
        String menuFormat;
        String bodyFormat;
        ArrayList<String> messages = new ArrayList<>();

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

        if(turn.equals(username) && actions!=null) {
            printActions(length);
        }

        if(!turn.equals(username)) {
            messages.add("Wait yuor turn");
        }

        if(messages.size() != 0) {
            printMessages(length, messages);
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
