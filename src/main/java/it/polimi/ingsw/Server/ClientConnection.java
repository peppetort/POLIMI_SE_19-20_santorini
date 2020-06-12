package it.polimi.ingsw.Server;


import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Observer.Observer;

/**
 * Interface used to create a {@link SocketClientConnection}
 */
public interface ClientConnection  {

    String getUsername();

    void closeConnection();

    void addObserver(Observer<Message> observer);

    void send(Object message);
}
