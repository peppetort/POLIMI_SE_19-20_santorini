package it.polimi.ingsw.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12346;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    public HashMap<String, Session> disponibleSession = new HashMap<>();

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public HashMap<String, Session> getSessions(){
        HashMap<String, Session> sessions = (HashMap<String, Session>) disponibleSession.clone();
        return sessions;
    }

    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                System.out.println("New socket connected:"+socketConnection.getUsername());
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}
