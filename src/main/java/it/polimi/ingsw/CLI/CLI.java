package it.polimi.ingsw.CLI;

import it.polimi.ingsw.Client.ClientBoard;
import it.polimi.ingsw.Client.ClientStatus;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import java.util.HashMap;
import java.util.Scanner;

public class CLI extends Observable<Object> implements Observer{
    private final Printer printer;

    private static Scanner reader = new Scanner(System.in).useDelimiter("\n");

   // private State state;
    public CLI(){
        printer = new Printer();
        // this.state = State.START;
    }

    public void setClientBoard(ClientBoard clientBoard){
        printer.setClientBoard(clientBoard);
    }

    public void setClientStatus(ClientStatus clientStatus){
        printer.setClientStatus(clientStatus);
    }

    private void takeInput(){
        String input;
        input = reader.nextLine();
        System.out.println(input);
    }

    public void startMenu() {
            try {
                reader = new Scanner(System.in);
                boolean correct;

                System.out.println("List of commands:");
                System.out.println("JOIN (join an existing session)");
                System.out.println("CREATE (create a new game)");
                System.out.print("\n> ");

                do {
                    String input = reader.nextLine();
                    if (input.toUpperCase().equals("JOIN") || input.toUpperCase().equals("CREATE")) {
                        switch (input.toUpperCase()) {
                            case "JOIN":
                                System.out.println("List of available session:");
                                //TODO: rimuovere "ciao"
                                notify(new PlayerRetrieveSessions("ciao"));
                                break;
                            case "CREATE":
                                create();
                                break;
                        }
                        correct = true;
                    }else {
                        System.out.println("Type a valid command");
                        correct = false;
                }
                }while(!correct);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    public void create() {

        String input;
        int question = 0;
        String username = null;
        String session = null;
        int players = 2;
        boolean correct;
        boolean simple = false;

        do {
            switch (question) {
                case 0:
                    System.out.println("Insert your name:");
                    System.out.print("> ");
                    input = reader.nextLine();
                    username = input;
                    correct = true;
                    break;
                case 1:
                    System.out.println("Insert name of the session:");
                    System.out.print("> ");
                    input = reader.nextLine();
                    session = input;
                    correct = true;
                    break;
                case 2:
                    System.out.println("Insert number of players:");
                    System.out.print("> ");
                    input = reader.nextLine();
                    players = Integer.parseInt(input);
                    correct = players >= 2 && players <= 3;
                    break;
                case 3:
                    System.out.println("Do you want to use cards? Y/N");
                    System.out.print("> ");
                    input = reader.nextLine();
                    if (input.toUpperCase().equals("Y")) {
                        simple = false;
                        correct = true;
                    } else if (input.toUpperCase().equals("N")) {
                        simple = true;
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
        } while (!input.toUpperCase().equals("ESC") && question < 4);

        if (!input.toUpperCase().equals("ESC")) {
            PlayerCreateSessionMessage createMessage = new PlayerCreateSessionMessage(username, session, players, simple);
            //state = State.WAIT;
            notify(createMessage);
        }else{
            //state = State.START;
        }
    }


    public void join(SessionListMessage message) {
        HashMap<String,Integer> participants = message.getParticipants();
        HashMap<String,Boolean> cards = message.getCards();
        String session;
        String username;
        boolean correct = false;

        printer.printAvailableSession(participants, cards);

        if(participants.size() != 0){

            //TODO: perché non chiederlo dopo aver scelto la sessione?
            System.out.println("Insert your username (type esc to go back to startMenu):");
            System.out.print("> ");
            username = reader.nextLine().toUpperCase();

            if (username.toUpperCase().equals("ESC")) {

                startMenu();
            } else {
                System.out.println("Insert the name of the session you want to join:");
                System.out.print("> ");

                do {
                    session = reader.nextLine();
                    if (participants.containsKey(session)) {
                        correct = true;
                        notify(new PlayerSelectSession(session, username));
                    }
                    if (!correct) {
                        System.out.println("This session doesn't exists!");
                    }
                } while (!correct && !session.toUpperCase().equals("ESC"));

                if (session.toUpperCase().equals("ESC")) {
                    startMenu();
                }
            }
        }else {
            startMenu();
        }
    }


    @Override
    public void update(Object message) {

        if (message instanceof Integer) {
            if ((Integer) message == 0) {
                this.startMenu();
            }else if ((Integer) message == 1) {
                printer.printBoard();
                //TODO:eliminare
                System.out.println("print ");
            } else if ((Integer) message == 2) {
                printer.printStatus();
                this.takeInput();
                printer.printStatus();
                //TODO: eliminare
                System.out.println("print status");
            } else if ((Integer) message == 3) {
                printer.printAllCards();
                //TODO: eliminare
                System.out.println("print all cards");
            } else if ((Integer) message == 4) {
                printer.printDeck();
                //TODO:eliminare
                System.out.println("print deck");
            }
        } else if(message instanceof SessionListMessage){
            join((SessionListMessage)message);
        } else {
            System.out.println(message);
        }
    }

}
















