package it.polimi.ingsw.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server is used to handle each {@link SocketClientConnection} separately thanks to an {@link ExecutorService}
 * which creates a thread pool.
 */

public class Server {

    private static final int PORT = 12346;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    public HashMap<String, Session> availableSessions = new HashMap<>();

    /**
     * Constructor which initialize the server port which is a final {@link Integer}.
     * @throws IOException
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    /**
     * Return a list of available sessions which the {@link it.polimi.ingsw.Client.Client} can join.
     * @return
     */
    public HashMap<String,Session> getAvailableSessions(){
        return availableSessions;
    }

    /**
     * It accepts each {@link it.polimi.ingsw.Client.Client} connection and creates a new {@link SocketClientConnection}
     * based on the socket accepted which will be handled on a thread in the {@link ExecutorService}.
     */
    public void startServer() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            }catch (IOException e) {
                System.err.println("Connection Error! " + e);
            }
        }
    }
}
