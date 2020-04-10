package it.polimi.ingsw.Server;

import it.polimi.ingsw.Exceptions.FullSessionException;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private Session session;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

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
        System.out.println("Deregistering client...");
        session.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        Scanner in;
        String playerName;
        String read;
        int status = 0;

        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            playerName = in.nextLine();

            while (status == 0) {
                send("create/take part?");
                read = in.nextLine();

                if (read.compareTo("create") == 0) {
                    status = 1;
                } else if (read.compareTo("take part") != 0) {
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
                    sessionName = in.nextLine();
                    if (server.disponibleSession.containsKey(sessionName)) {
                        valid = false;
                        send("Session named " + sessionName + " already exists! Please insert another name.");
                    }
                } while (!valid);

                do {
                    valid = true;
                    send("Number of competitors (2-3):");
                    participantsNumb = Integer.parseInt(in.nextLine());
                    if (participantsNumb != 2 && participantsNumb != 3) {
                        valid = false;
                        send("Invalid! Try again!");
                    }
                } while (!valid);

                do {
                    valid = true;
                    send("Do you want to use cards? (y/n):");
                    read = in.nextLine();
                    if (read.compareTo("y") == 0) {
                        simple = false;
                    } else if (read.compareTo("n") == 0) {
                        simple = true;
                    } else {
                        valid = false;
                        send("Invalid! Try again!");
                    }
                } while (!valid);

                session = new Session(this, playerName, participantsNumb, simple, server);
                server.disponibleSession.put(sessionName, session);
            }

            if (status == 2) {
                String selected;
                boolean valid;

                do {
                    valid = true;
                    send("Select session to join:");
                    send(server.disponibleSession.keySet().toString());
                    selected = in.nextLine();
                    session = server.disponibleSession.get(selected);
                    if (session == null) {
                        valid = false;
                        send("Invalid! Try again!");
                    } else {
                        try {
                            session.addParticipant(this, playerName, selected);
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

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        } finally {
            close();
        }
    }
}

