package it.polimi.ingsw.Server;

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

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
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
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override

        public void asyncSend(final Object message){
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
            String name;
            String choice;
            try{
                in = new Scanner(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                send("Welcome!\nWhat is your name?");
                String read = in.nextLine();
                name = read;
                if(server.disponibleSession.isEmpty())
                {
                    choice="create";
                    send("No disponible session, you have to create a new session");
                }else {
                    do {
                        send("create/take part?");
                        read = in.nextLine();
                        choice = read;
                    } while (choice.compareTo("create") != 0 && choice.compareTo("take part") != 0);
                }
                    if (choice.compareTo("create") == 0) {
                        send("create, name of session:");
                        read = in.nextLine();
                        Session session = new Session(this, name, 2, false, server);
                        //server.disponibleSession.add(session);
                        server.disponibleSession.put(read, session);
                    } else if (choice.compareTo("take part") == 0) {

                        send(server.disponibleSession.keySet().toString());
                        send("select");
                        read = in.nextLine();
                        server.disponibleSession.get(read).addParticipant(this, name, read);
                    }

                while(isActive()){
                    read = in.nextLine();
                    notify(read);
                }
            } catch (IOException | NoSuchElementException e) {
                System.err.println("Error!" + e.getMessage());
            }finally{
                close();
            }
        }
    }

