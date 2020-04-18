package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.BoardUpdate;
import it.polimi.ingsw.Messages.InitializePlayersMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;

import java.util.ArrayList;

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
               if(inputs[0].compareTo("MOVE")==0)
               {
                   handleMove(Integer.valueOf(inputs[1]), Integer.valueOf(inputs[2]));
               }else
               if(inputs[0].compareTo("BUILD")==0) {

                   handleBuild(Integer.valueOf(inputs[1]),Integer.valueOf(inputs[2]));
               }else
               if(inputs[0].compareTo("END")==0) {

                   handleEnd();
               }else
               if(inputs[0].compareTo("START")==0){
                   handleStart(Integer.valueOf(inputs[1]));
               }else
               if(inputs[0].compareTo("PLACE")==0){
                   handlePlacing(Integer.valueOf(inputs[1]),Integer.valueOf(inputs[2]),Integer.valueOf(inputs[3]),Integer.valueOf(inputs[4]));
               }else
               {
                   throw new IllegalArgumentException();
               }

           }catch(IllegalArgumentException e) {
               clientConnection.asyncSend("Input Error!");
           }catch(Exception e){
               clientConnection.asyncSend(e.getMessage());
           }
        }
    }

    private ClientConnection clientConnection;

    public RemoteView(Player player, Player opponent, ClientConnection c) {
        super(player,opponent);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
        c.asyncSend("Your opponent is: " + opponent);
        c.asyncSend("Waiting.....");
    }

    @Override
    public void update(Message message) {
        if(message instanceof BoardUpdate){
            clientConnection.asyncSend(message);
        }else {
            System.out.println("Malformed message");
        }

    }
    @Override
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }
}
