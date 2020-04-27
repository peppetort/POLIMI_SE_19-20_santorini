package it.polimi.ingsw.Server;

import it.polimi.ingsw.Exceptions.FullSessionException;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private final Socket socket;
    private ObjectOutputStream out;
    private final Server server;
    private Session session;
    private String username;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        closeConnection();
        System.out.println("Deregister client " + username + ":" + session);
        session.deregisterConnection(username);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public synchronized void send(final Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void setName(Scanner in) {
        boolean valid = false;
        do {
            send("Insert your name");
            username = in.nextLine().toUpperCase();
            try {
                if (session.getWaitingConnection().get(username) == null) {
                    valid = true;
                } else {
                    send(username + " already exists.\nTry again:");
                }
            } catch (NullPointerException e) {
                valid = true;
            }
        } while (!valid);
    }

    @Override
    public void run() {
        Scanner in;
        String read;
        int status = 0;

        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome to Santorini CLI Game!");

            while (status == 0) {
                send("1) To create a new game enter: create\n" +
                        "2) To participate in an existing game enter: join");
                read = in.nextLine().toUpperCase();

                if (read.compareTo("CREATE") == 0) {
                    status = 1;
                } else if (read.compareTo("JOIN") == 0) {
                    if (server.disponibleSession.isEmpty()) {
                        send("No disponible session");
                    } else {
                        status = 2;
                    }
                } else {
                    send("Invalid choice! Please try again");
                }
            }


            if (status == 1) {
                String sessionName;
                int participantsNumb;
                boolean simple = true;
                boolean valid;

                do {
                    valid = true;
                    send("Name of Session:");
                    sessionName = in.nextLine().toUpperCase();
                    if (server.disponibleSession.containsKey(sessionName)) {
                        valid = false;
                        send("Session named " + sessionName + " already exists! Please insert another name.");
                    }
                } while (!valid);

                do {
                    valid = true;
                    send("Number of competitors (2-3):");
                    try {
                        participantsNumb = Integer.parseInt(in.nextLine());
                    }catch (NumberFormatException e){
                        participantsNumb = -1;
                    }
                    if (participantsNumb != 2 && participantsNumb != 3) {
                        valid = false;
                        send("Invalid! Try again!");
                    }
                } while (!valid);

                do {
                    valid = true;
                    send("Do you want to use cards? (y/n):");
                    read = in.nextLine().toUpperCase();
                    if (read.compareTo("Y") == 0) {
                        simple = false;
                    } else if (read.compareTo("N") == 0) {
                        simple = true;
                    } else {
                        valid = false;
                        send("Invalid! Try again!");
                    }
                } while (!valid);
                setName(in);
                session = new Session(this, participantsNumb, simple, server, sessionName);
                server.disponibleSession.put(sessionName, session);
            }

            if (status == 2) {
                String selected;
                boolean valid;

                do {
                    valid = true;
                    send("Select session to join:");
                    StringBuilder name = new StringBuilder();
                    StringBuilder cards = new StringBuilder();
                    StringBuilder waitingPlayers = new StringBuilder();
                    HashMap<String, Session> disponibleSession = server.disponibleSession;

                    for (String session : disponibleSession.keySet()) {
                        name.setLength(0);
                        cards.setLength(0);
                        waitingPlayers.setLength(0);
                        name.append("Name: ");
                        cards.append("Cards: ");
                        waitingPlayers.append("Waiting Players: ");
                        name.append(session);
                        if (disponibleSession.get(session).isSimple()) {
                            cards.append("NO");
                        } else {
                            cards.append("YES");
                        }
                        waitingPlayers.append(disponibleSession.get(session).getWaitingConnection().size());
                        send(name.toString() + "\n" + cards.toString() + "\n" + waitingPlayers.toString() + "\n\n");
                    }

                    selected = in.nextLine().toUpperCase();
                    session = server.disponibleSession.get(selected);

                    if (session == null) {
                        valid = false;
                        send("Invalid! Try again!");
                    } else {
                        setName(in);
                        try {
                            session.addParticipant(this);
                        } catch (FullSessionException e) {
                            valid = false;
                            send(e.getMessage());
                        }


                    }
                } while (!valid);
            }

            while (isActive()) {
                read = in.nextLine();
                notify(read);
            }

        } catch (IOException | NoSuchElementException | InterruptedException e) {
            System.err.println("Error!" + e.getMessage());
        } finally {
            close();
        }
    }
}

