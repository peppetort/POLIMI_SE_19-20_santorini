package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.Message;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

       @Override
        public void update(String message) {
           //TODO:
        }
    }

    private ClientConnection clientConnection;

    public RemoteView(Player player, String opponent, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
        c.asyncSend("Your opponent is: " + opponent);

    }

    @Override
    public void update(Message message)
    {

    }
    @Override
    protected void showMessage(Object message) {

    }
}
