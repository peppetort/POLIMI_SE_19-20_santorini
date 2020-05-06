package it.polimi.ingsw.Server;

import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private final Socket socket;

    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private final Server server;
    private Session session;
    private String username;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    private synchronized boolean isActive() {
        return active;
    }


    //TODO: gestire bene cosa accade se il client si disconnette

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

    //TODO: ?
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
    public synchronized void send(final Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void run() {
        Object inputObject;
        try {

            //TODO: eliminare
            send("message from server: connected");

            while (isActive()) {
                //todo: provo ad inviare senza ricevere

                inputObject = in.readObject();

                 if (inputObject instanceof PlayerRetrieveSessions) {
                     //TODO: eliminare
                     System.out.println("Player retrieve");
                     send("server: join request arrived....");

                     try {
                         //TODO:eliminare
                         System.out.println("Sending the retrieval message");

                         HashMap<String, Session> availableSessions = server.getAvailableSessions();
                         SessionListMessage sessionListMessage = new SessionListMessage();

                         for(String session: availableSessions.keySet()){
                             sessionListMessage.addSession(session, availableSessions.get(session).getParticipant(), !availableSessions.get(session).isSimple());
                         }
                         send(sessionListMessage);
                     }catch(Exception e){
                         System.out.println("Exception occurred");
                         System.err.println(e.getMessage());
                     }

                 } else if (inputObject instanceof PlayerCreateSessionMessage) {

                     //TODO:eliminare
                     send("SERVER SAYS: CREATING MATCH");

                     String username = ((PlayerCreateSessionMessage) inputObject).getUsername();
                     String sessionID = ((PlayerCreateSessionMessage) inputObject).getSession();

                     //TODO: controllare che non esista una sessione con lo stesso nome
                     //TODO: controllare che players sia tra 2 e 3
                     int players = ((PlayerCreateSessionMessage) inputObject).getPlayers();
                     boolean cards = ((PlayerCreateSessionMessage) inputObject).isSimple();

                     this.username = username;
                     session = new Session(this, players, cards, server, sessionID);
                     server.availableSessions.put(sessionID, session);

                } else if (inputObject instanceof PlayerSelectSession) {
                     //TODO: eliminare
                     send("server: joining session");
                     String sessionID;
                     this.username = ((PlayerSelectSession) inputObject).getUsername();
                     sessionID = ((PlayerSelectSession) inputObject).getSessionID();
                     server.availableSessions.get(sessionID).addParticipant(this);
                 }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}

