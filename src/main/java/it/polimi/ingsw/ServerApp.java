package it.polimi.ingsw;

import it.polimi.ingsw.Server.Server;

import java.io.IOException;

public class ServerApp
{
    public static void main( String[] args )
    {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Server initialization failed: " + e.getMessage() + "!");
        }
    }
}
