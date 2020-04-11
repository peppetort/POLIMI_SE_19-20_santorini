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
           try{
               message= message.toUpperCase();

               String[] inputs = message.split(" ");
               String[] coordinate;
               String[] card;
               if(inputs[0].compareTo("DECK")==0)
               {
                   card=inputs[1].split(",");
                   handleDeck(card[0],card[1],card[2]);
               }else
               if(inputs[0].compareTo("CARD")==0)
               {
                   handleCard(inputs[1]);
               }else
               if(inputs[0].compareTo("SELECT")==0)
               {
                   coordinate=inputs[1].split(",");
                   handleSelection(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]));
               }else
               if(inputs[0].compareTo("MOVE")==0)
               {
                   coordinate=inputs[1].split(",");
                   handleMove(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]));
               }else
               if(inputs[0].compareTo("BUILD")==0)
               {
                   coordinate=inputs[1].split(",");
                   handleBuild(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]));
               } else
                   throw new IllegalArgumentException();

           }catch(IllegalArgumentException e){
               clientConnection.asyncSend("Error!");

           }
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
            showMessage("tocca a te");
    }
    @Override
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }
}
