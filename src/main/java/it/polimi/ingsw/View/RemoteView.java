package it.polimi.ingsw.View;


import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;

import java.util.ArrayList;

/**
 * {@link RemoteView} extends the abstract class {@link View}. The {@link it.polimi.ingsw.Server.Session} instantiate
 * a {@link RemoteView} for each {@link Player}.
 * It observs {@link it.polimi.ingsw.Model.Game} (the model for the MVC pattern).
 */
public class RemoteView extends View {


    /**
     * Private class used to handle each {@link Message} notified by the {@link it.polimi.ingsw.Server.SocketClientConnection}.
     * Observs the {@link it.polimi.ingsw.Server.SocketClientConnection}.
     */
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
                }else if(message instanceof PlayerChatMessage){
                    handleChat(message);
                }
            } catch (Exception e) {
                clientConnection.send(e.getMessage());
            }
        }
    }

    private final ClientConnection clientConnection;

    /**
     * Constructor of the class. An object {@link MessageReceiver} is used to observ a {@link it.polimi.ingsw.Server.SocketClientConnection}.
     * While this class is an {@link Observer} of the {@link it.polimi.ingsw.Model.Game}.
     * @param player
     * @param c
     */
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

    /**
     * The message is sent to the {@link it.polimi.ingsw.Client.Client} via {@link it.polimi.ingsw.Server.SocketClientConnection}.
     * @param message
     */
    @Override
    public void update(Message message) {
        clientConnection.send(message);
    }

}
