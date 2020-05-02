package it.polimi.ingsw.Server;

import it.polimi.ingsw.Exceptions.FullSessionException;
import it.polimi.ingsw.Messages.PlayerCreateSessionMessage;
import it.polimi.ingsw.Messages.PlayerRetrieveSessions;
import it.polimi.ingsw.Messages.PlayerSelectSession;
import it.polimi.ingsw.Messages.SessionListMessage;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
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
        Object inputObject = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            inputObject = (new ObjectInputStream(socket.getInputStream())).readObject();;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (isActive()) {
            try {
                if(inputObject instanceof Object){
                    System.out.println("Object received");
                }
                if (inputObject instanceof PlayerRetrieveSessions) {
                    send(new SessionListMessage(server.getSessions()));
                } else if (inputObject instanceof PlayerCreateSessionMessage) {

                    String username = ((PlayerCreateSessionMessage) inputObject).getUsername();
                    String sessionID = ((PlayerCreateSessionMessage) inputObject).getSession();
                    int players = ((PlayerCreateSessionMessage) inputObject).getPlayers();
                    boolean cards = ((PlayerCreateSessionMessage) inputObject).isCards();
                    session = new Session(this, players, cards, server, sessionID);
                    server.disponibleSession.put(sessionID, session);
                    session.getWaitingConnection().put(username, this);
                } else if (inputObject instanceof PlayerSelectSession) {
                    server.disponibleSession.get(((PlayerSelectSession) inputObject).getSessionID()).addParticipant(this);
                }
            } catch (Exception e) {
            }
        }
    }
}

