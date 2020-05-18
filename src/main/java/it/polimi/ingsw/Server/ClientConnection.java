package it.polimi.ingsw.Server;


import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Observer.Observer;

public interface ClientConnection  {

    String getUsername();

    void closeConnection();

    void addObserver(Observer<Message> observer);

    void send(Object message);
}
