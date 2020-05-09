package it.polimi.ingsw.CLI;

import it.polimi.ingsw.Client.Actions;
import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Client.ClientBoard;
import it.polimi.ingsw.Client.ClientStatus;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI extends Observable<Object> implements Observer{
    private final Printer printer;
    private Client client;
    private static Scanner reader = new Scanner(System.in).useDelimiter("\n");

   // private State state;
    public CLI(Client client){
        printer = new Printer();
        this.client = client;
        // this.state = State.START;
    }

    public void setClientBoard(ClientBoard clientBoard){
        printer.setClientBoard(clientBoard);
    }

    public void setClientStatus(ClientStatus clientStatus){
        printer.setClientStatus(clientStatus);
    }

    private void takeInput(){
        //TODO IL CONTROLLO DELLA CORRETTEZZA DEVE ESSERE FATTO LATO SERVER
        String input="";
        String[] data;
        Actions action;
        System.out.println("Insert action:");
        System.out.print(">");

        input = reader.nextLine();
        data = input.split(" ");
        action = Actions.valueOf(data[0].toUpperCase());
        System.out.println(action);
        switch(action){
            case UNDO:
                notify(new PlayerUndoMessage());
                break;
            case BUILD:
                notify(new PlayerBuildMessage(Integer.valueOf(data[1]),Integer.valueOf(data[2])));
                break;
            case END:
                notify(new PlayerEndMessage());
                break;
            case MOVE:
                notify(new PlayerMoveMessage(Integer.valueOf(data[1]),Integer.valueOf(data[2])));
                break;
            case PLACE:
                notify(new PlayerPlacePawnsMessage(Integer.valueOf(data[1]),Integer.valueOf(data[2]),Integer.valueOf(data[3]),Integer.valueOf(data[4])));
                break;
            case SELECT:
                notify(new PlayerSelectMessage(Integer.valueOf(data[1])));
                break;
            case CARD:
                notify(new PlayerCardChoiceMessage(God.valueOf(data[1].toUpperCase())));
                break;
            case DECK:
                ArrayList<God> deck = new ArrayList<>();
                for(String s: data){
                    if(s!=data[0]){
                        deck.add(God.valueOf(s.toUpperCase()));
                    }
                }
                notify(new PlayerDeckMessage(deck));
                break;
            case BUILD_DOME:
                notify(new PlayerBuildDomeMessage(Integer.valueOf(data[1]),Integer.valueOf(data[2])));
                break;
        }

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
            } else if ((Integer) message == 2) {
                printer.printStatus();
                //TODO : ANDREBBE BENE SOLO SE HO USERNAME UNIVOCI
                if(client.getStatus().myTurn()) {
                    takeInput();
                }
            } else if ((Integer) message == 3) {
                printer.printAllCards();
            } else if ((Integer) message == 4) {
                printer.printDeck();
            }
        } else if(message instanceof SessionListMessage){
            join((SessionListMessage)message);
        } else {
            System.out.println(message);
        }
    }

}
















