package it.polimi.ingsw;


import it.polimi.ingsw.Client.Client;

public class ClientApp {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 12346, 1);
        try {
            client.startClient();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}