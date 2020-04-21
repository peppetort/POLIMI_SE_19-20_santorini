package it.polimi.ingsw.Client;


import it.polimi.ingsw.Messages.BoardUpdate;
import it.polimi.ingsw.Messages.MenuMessage;
import it.polimi.ingsw.Messages.TurnMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private final String ip;
    private final int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private boolean active = true;

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    Object inputObject = socketIn.readObject();
                    if (inputObject instanceof String) {
                        System.out.println((String) inputObject);
                    } else if (inputObject instanceof BoardUpdate) {
                        printBoard((BoardUpdate) inputObject);
                    } else if (inputObject instanceof MenuMessage) {
                        printMenu((MenuMessage) inputObject);
                    } else if (inputObject instanceof TurnMessage) {
                        printTurn((TurnMessage) inputObject);
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut) {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    String inputLine = stdin.nextLine();
                    socketOut.println(inputLine);
                    socketOut.flush();
                }
            } catch (Exception e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    private void printBoard(BoardUpdate message) {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";
        boolean pawnThere;


        System.out.println("BOARD");
        System.out.println("    0   1   2   3   4");
        int c = 0;
        try {
            for (int i = 0; i < 5; i++) {
                System.out.print(i + "  ");
                for (int j = 0; j < 5; j++) {
                    pawnThere = false;
                    System.out.print((message.getBoardData())[c] + "-");
                    c++;
                    for (String s : message.getWorkers().keySet()) {
                        if (message.getWorkers().get(s)[0] == i && message.getWorkers().get(s)[1] == j) {
                            pawnThere = true;
                            switch (message.getColors().get(s).getValue()) {
                                case 1:
                                    System.out.print(ANSI_BLUE + "1 " + ANSI_RESET);
                                    break;
                                case 2:
                                    System.out.print(ANSI_RED + "1 " + ANSI_RESET);
                                    break;
                                case 3:
                                    System.out.print(ANSI_GREEN + "1 " + ANSI_RESET);
                                    break;
                            }
                        }
                        if (message.getWorkers().get(s)[2] == i && message.getWorkers().get(s)[3] == j) {
                            pawnThere = true;
                            switch (message.getColors().get(s).getValue()) {
                                case 1:
                                    System.out.print(ANSI_BLUE + "2 " + ANSI_RESET);
                                    break;
                                case 2:
                                    System.out.print(ANSI_RED + "2 " + ANSI_RESET);
                                    break;
                                case 3:
                                    System.out.print(ANSI_GREEN + "2 " + ANSI_RESET);
                                    break;
                            }
                        }

                    }
                    if (!pawnThere) {
                        System.out.print("x ");
                    }
                }
                System.out.println("");
            }
        } catch (NullPointerException e) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    System.out.print((message.getBoardData())[c] + " ");
                    c++;
                }
                System.out.println(" ");
            }
        }
    }

    public void printMenu(MenuMessage message){
        ArrayList<String> actions = message.getActions();
        System.out.println("+--------------------------ACTIONS----------------------------+");
        for(String s: actions){
            System.out.println(s);
        }
        System.out.println("+-------------------------------------------------------------+");
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    public void printTurn(TurnMessage message) {
        for(String s: message.getTurn().keySet()){
            if(message.getTurn().get(s)){
                System.out.println("Turno di "+ s);
            }
        }
    }
}
