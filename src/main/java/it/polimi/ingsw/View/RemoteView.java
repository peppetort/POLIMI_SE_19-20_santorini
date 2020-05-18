package it.polimi.ingsw.View;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;

import java.util.ArrayList;

public class RemoteView extends View {


    private class MessageReceiver implements Observer<Message> {

        @Override
        public void update(Message message) {
            try {

                if(message instanceof PlayerPlacePawnsMessage){
                    handlePlacing(message);
                }else if(message instanceof PlayerMoveMessage){
                    handleMove(message);
                }else if(message instanceof PlayerBuildMessage){
                    handleBuild(message);
                }else if(message instanceof PlayerSelectMessage){
                    handleSelect(message);
                }else if(message instanceof PlayerEndMessage){
                    handleEnd(message);
                }else if(message instanceof PlayerCardChoiceMessage){
                    handleCardChoice(message);
                }else if(message instanceof PlayerDeckMessage){
                    handleDeck(message);
                }else if(message instanceof PlayerUndoMessage){
                    handleUndo(message);
                }else if(message instanceof PlayerBuildDomeMessage){
                    handleBuildDome(message);
                }


            } catch (Exception e) {
                clientConnection.send(e.getMessage());
            }
        }
    }

    private final ClientConnection clientConnection;

    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
        ArrayList<Player> opponents = player.getSession().getPlayers();
        ArrayList<Color> players = new ArrayList<>();
        players.add(player.getColor());

        StringBuilder opponentsMessage;
        if(opponents.size() == 1){
            opponentsMessage = new StringBuilder("Your opponent is: ");
        }else {
            opponentsMessage = new StringBuilder("Your opponents are: ");
        }


        for (Player p : opponents) {
            String opponentUsername = p.getUsername();
            if (!opponentUsername.equals(player.getUsername())) {
                opponentsMessage.append(opponentUsername).append(" ");
                players.add(p.getColor());
            }
        }
        c.send(opponentsMessage.toString());

        ClientInitMessage message = new ClientInitMessage(player.getUsername(), players);
        c.send(message);

    }

    @Override
    public void update(Message message) {

        if (message instanceof ActionsUpdateMessage) {
            clientConnection.send(message);
        } else if (message instanceof TurnUpdateMessage) {
            clientConnection.send(message);
        } else if (message instanceof BoardUpdatePlaceMessage) {
            clientConnection.send(message);
        } else if (message instanceof BoardUpdateBuildMessage) {
            clientConnection.send(message);
        } else if (message instanceof WinMessage) {
            clientConnection.send(message);
        } else if (message instanceof CardUpdateMessage) {
            clientConnection.send(message);
        } else if (message instanceof DeckUpdateMessage) {
            clientConnection.send(message);
        }else if(message instanceof LostMessage){
            clientConnection.send(message);
        } else if(message instanceof BoardUndoMessage) {
            clientConnection.send(message);
        }else if(message instanceof InvalidChoiceMessage){
            clientConnection.send(message);
        }else {
            System.err.println("Malformed message: " + message.toString());
        }
    }

}
