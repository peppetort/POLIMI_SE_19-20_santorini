package it.polimi.ingsw.Server;

import it.polimi.ingsw.Observer.Observer;

public interface ClientConnection {

    String getUsername();

    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);
}
