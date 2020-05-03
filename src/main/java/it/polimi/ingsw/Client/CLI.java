package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.Box;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI extends Observable<Message> implements Observer {
    private ClientBoard clientBoard;
    private ClientStatus clientStatus;
    private final String lengthMarker = "-";
    private final String widthMarker = "+";
    private static Scanner reader = new Scanner(System.in).useDelimiter("\n");

    public void setClientBoard(ClientBoard clientBoard) {
        this.clientBoard = clientBoard;
    }

    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    public void print() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";

        String title = "BOARD";
        String menuFormat;
        String bodyFormat;

        String lengthMarker = "-";
        String widthMarker = "+";

        int maxLength = 18;
        Box[][] board = clientBoard.getBoard();

        menuFormat = "%-";
        menuFormat += Integer.toString(11 + maxLength / 2);
        menuFormat += "s%s%";
        menuFormat += Integer.toString(12 + maxLength / 2);
        menuFormat += "s%n";

        int row = 0;

        int length = 2 * maxLength + 6;

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

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);


        bodyFormat = "%-" + (maxLength - 12) + "s%6s%6s%6s%6s%6s%" + (maxLength - 8) + "s";
        System.out.printf(bodyFormat, widthMarker, 0, 1, 2, 3, 4, widthMarker);
        System.out.print("\n");

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);


        for (int x = 0; x < 5; x++) {
            StringBuilder sRow = new StringBuilder(String.format("%-" + (maxLength - 12) + "s%s", widthMarker, row));
            for (int y = 0; y < 5; y++) {

                try {
                    switch (board[x][y].getPlayer()) {
                        case GREEN:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_GREEN + board[x][y].getWorker() + ANSI_RESET));
                            break;
                        case RED:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_RED + board[x][y].getWorker() + ANSI_RESET));
                            break;
                        case BLUE:
                            sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_BLUE + board[x][y].getWorker() + ANSI_RESET));
                            break;
                    }
                } catch (NullPointerException e) {
                    sRow.append(String.format("%4s-%s", board[x][y].getLevel(), ANSI_RESET + 0));
                }
            }
            sRow.append(String.format("%" + (maxLength - 9) + "s", widthMarker));
            System.out.print(sRow);
            System.out.print("\n");
            System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);
            row++;
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);

        System.out.print(widthMarker + " ");
        for (int i = 0; i < length; i++) {
            System.out.print(lengthMarker);
        }
        System.out.print(" " + widthMarker);
        System.out.print("\n");
        System.out.print("\n");

    }

    private synchronized void printActions(int length) {

        String actionsLabel = "ACTION";
        String bodyFormat;
        ArrayList<String> actions = clientStatus.getActions();

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

        for (String act : actions) {
            bodyFormat = "%-3s%s%" + (length + 1 - act.length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, "> " + act.toUpperCase(), widthMarker);
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);

    }

    //TODO: spostare in una classe CLI
    private synchronized void printMessages(int length) {
        String messagesLabel = "MESSAGES";
        String bodyFormat;

        ArrayList<String> messages = clientStatus.getMessages();

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

        for (String message : messages) {
            bodyFormat = "%-3s%s%" + (length + 3 - message.length() - 2) + "s%n";
            System.out.printf(bodyFormat, widthMarker, message, widthMarker);
        }

        System.out.printf(("%s%" + (length + 3) + "s%n"), widthMarker, widthMarker);
    }

    //TODO: sposatre in una classe CLI
    public synchronized void printAllCards() {
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

        for (God g : God.values()) {
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

    //TODO: spostare in una classe CLI
    public synchronized void printDeck() {
        String title = "AVAILABLE CARDS";
        String menuFormat;
        String bodyFormat;

        ArrayList<God> deck = clientStatus.getDeck();

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

        for (God name : deck) {
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

    //TODO: spostare in classe CLI
    public synchronized void printStatus() {
        String title = "STATUS";
        String menuFormat;
        String bodyFormat;

        String username = clientStatus.getUsername();
        String card = clientStatus.getCard();
        String turn = clientStatus.getTurn();
        Color color = clientStatus.getColor();
        ArrayList<String> actions = clientStatus.getActions();
        ArrayList<String> messages = clientStatus.getMessages();


        StringBuilder usernameLabel = new StringBuilder("Username: ").append(username);
        StringBuilder colorLabel = new StringBuilder("Color: ").append(color);

        StringBuilder cardLabel = null;
        if (card != null) {
            cardLabel = new StringBuilder("Card: ").append(card);
        }

        StringBuilder turnLabel = null;

        if (turn != null) {
            turnLabel = new StringBuilder("Turn: ").append(turn);
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

        if (actions != null) {
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

        if (turn != null && turn.equals(username) && actions != null) {
            printActions(length);
        }

        if (messages.size() != 0) {
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

    public void startMenu() {
        while (true) {
            try {
                reader = new Scanner(System.in);

                System.out.println("WELCOME TO SANTORINI");
                System.out.println("list of commands:");
                System.out.println("JOIN (join an existing session)");
                System.out.println("CREATE (create a new game)");

                String input = reader.nextLine();

                switch (input.toUpperCase()) {
                    case "JOIN":
                        join();
                        break;
                    case "CREATE":
                        create();
                        break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void create() throws IOException {

        String input;
        int question = 0;
        String username = null;
        String session = null;
        int players = 2;
        boolean correct = true;
        boolean cards = false;

        do {
            switch (question) {
                case 0:
                    System.out.println("Insert your name:");
                    input = reader.nextLine();
                    username = input;
                    correct = true;
                    break;
                case 1:
                    System.out.println("Insert name of the session:");
                    input = reader.nextLine();
                    session = input;
                    correct = true;
                    break;
                case 2:
                    System.out.println("Insert number of players:");
                    input = reader.nextLine();
                    players = Integer.valueOf(input);
                    if (players < 2 || players > 3) {
                        correct = false;
                    } else {
                        correct = true;
                    }
                    break;
                case 3:
                    System.out.println("Cards or no? Y/N");
                    input = reader.nextLine();
                    if (input.toUpperCase().equals("Y")) {
                        cards = true;
                        correct = true;
                    } else if (input.toUpperCase().equals("N")) {
                        cards = false;
                        correct = true;
                    } else {
                        correct = false;
                    }
                    break;
                default: throw new IllegalStateException();
            }
            if (correct) {
                question++;
            }
        } while (input.toUpperCase() != "ESC" && input.toUpperCase() != "BACK" && question < 4);

        if (input.toUpperCase() != "ESC" && input.toUpperCase() != "BACK") {
            System.out.println("creating the match.....");
            PlayerCreateSessionMessage createMessage = new PlayerCreateSessionMessage(username, session, players, cards);
            notify(createMessage);
        }
    }

    public void join() {
        System.out.println("List of available session:");
        notify(new PlayerRetrieveSessions("ciao"));
    }

    //todo: sistemare
    public void printAvailableSession(SessionListMessage message) {
        HashMap<String,Integer> partecipants = message.getPartecipants();
        HashMap<String,Boolean> cards = message.getCards();
        String input;
        boolean correct = false;

        for(String s: partecipants.keySet()){
            System.out.println(s);
            System.out.println(partecipants.get(s));
            System.out.println(cards.get(s));
        }

        System.out.println("Type the session to join || type esc/back to go back to start menu");
        try {
        input = reader.nextLine();
            while (input.toUpperCase() != "BACK" && input.toUpperCase() != "ESC" && !correct) {
                if (partecipants.containsKey(input)) {
                    correct = true;
                    //posso inviare la sessione da joinare
                    notify(new PlayerSelectSession(input));
                } else {
                    correct = false;
                }
                if (!correct) {
                    input = reader.nextLine();
                }
            }
        }catch (Exception e){}
    }

    @Override
    public void update(Object message) {
        if (message instanceof Integer) {
            if (Integer.valueOf((Integer) message) == 0) {
                this.startMenu();
            }else if (Integer.valueOf((Integer) message) == 1) {
                this.print();
            } else if (Integer.valueOf((Integer) message) == 2) {
                this.printStatus();
            } else if (Integer.valueOf((Integer) message) == 3) {
                this.printAllCards();
            } else if (Integer.valueOf((Integer) message) == 4) {
                this.printDeck();
            }
        } else if(message instanceof SessionListMessage){
            printAvailableSession((SessionListMessage)message);
        } else {
            System.out.println(message);
        }
    }
}
















