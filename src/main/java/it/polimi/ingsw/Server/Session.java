package it.polimi.ingsw.Server;


import it.polimi.ingsw.Model.Actions;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Exceptions.FullSessionException;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PlayerRemoveMessage;
import it.polimi.ingsw.Messages.TurnUpdateMessage;
import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.View.RemoteView;
import it.polimi.ingsw.View.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@link Session} is used to handle the pre-match. A "challenger" creates a {@link Session} where other {@link it.polimi.ingsw.Client.Client}
 * may join via {@link it.polimi.ingsw.Messages.PlayerSelectSession} message.
 */
public class Session extends Observable<Message> implements Serializable {
    private final String name;
    private final int participant;
    private final boolean simple;
    private transient final Server server;

    private transient final Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private transient final Map<String, ClientConnection> playingConnection = new HashMap<>();

    /**
     * Constructor which takes the settings for the {@link Game}.
     * @param creatorConnection
     * @param p
     * @param simple
     * @param server
     * @param sessionName
     * @throws InterruptedException
     */
    public Session(ClientConnection creatorConnection, int p, boolean simple, Server server, String sessionName) throws InterruptedException {
        this.participant = p;
        this.simple = simple;
        this.server = server;
        this.name = sessionName;
        addParticipant(creatorConnection);
    }

    public String getName() {
        return name;
    }

    public int getParticipant() {
        return participant;
    }

    public boolean isSimple() {
        return simple;
    }

    public synchronized Map<String, ClientConnection> getWaitingConnection() {
        return this.waitingConnection;
    }

    /**
     * When a {@link it.polimi.ingsw.Client.Client} succesful join a {@link Session} it will be added in the
     * waitingConnection map which will be cleared after its size reach the number of players selected for the
     * {@link Game} in the constructor. When this occurs a start method will be used to create the real {@link Game} on
     * the server and the match will start.
     * @param player
     */
    public synchronized void addParticipant(ClientConnection player){
        if (waitingConnection.size() < participant && playingConnection.size() == 0) {
            waitingConnection.put(player.getUsername(), player);
            if (waitingConnection.size() == participant) {
                server.availableSessions.remove(name);
                this.start();
            } else {
                player.send("Wait participants");
            }
        } else {
            throw new FullSessionException("Session is full!");
        }

    }

    /**
     * When someone disconnects from the {@link Server} it will be removed from the {@link Game} and each {@link it.polimi.ingsw.Client.Client}
     * which partecipate to the interested {@link Session} / {@link Game} will be notified with a {@link PlayerRemoveMessage}.
     * @param username
     */
    public synchronized void deregisterConnection(String username) {
        if (playingConnection.isEmpty()) {
            waitingConnection.remove(username);
            if (getWaitingConnection().isEmpty()) {
                server.availableSessions.remove(name);
            }
        } else {
            Message remove = new PlayerRemoveMessage(username);
            notify(remove);
            playingConnection.remove(username);
        }
    }


    /**
     * When the waitingConnection's size will reach the choosen player's number the game itself will start.
     */
    private void start(){

        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        Board board = new Board();

        playingConnection.putAll(waitingConnection);
        waitingConnection.clear();

        ClientConnection player1Connection = playingConnection.get(keys.get(0));
        ClientConnection player2Connection = playingConnection.get(keys.get(1));

        if(participant == 2){

            Game model = new Game(keys.get(0), keys.get(1), board, simple);
            Controller controller = new Controller(model);

            Player player1 = model.getPlayers().get(0);
            View player1View = new RemoteView(player1, player1Connection);

            player1.addObserver(player1View);
            board.addObserver(player1View);
//            controller.addObserver(player1View);
            player1View.addObserver(controller);
            model.addObserver(player1View);
            player1.setSession(model);

            Player player2 = model.getPlayers().get(1);
            View player2View = new RemoteView(player2, player2Connection);

            player2.addObserver(player2View);
            player2.setSession(model);

            board.addObserver(player2View);
//            controller.addObserver(player2View);
            player2View.addObserver(controller);
            model.addObserver(player2View);

            this.addObserver(controller);




            if(model.isSimple()){
                TurnUpdateMessage turnMessage = new TurnUpdateMessage(player2.getUsername(), player2.getColor());

                player1Connection.send(turnMessage);
                player2Connection.send(turnMessage);

                ActionsUpdateMessage actionMessage = new ActionsUpdateMessage();
                actionMessage.addAction(Actions.PLACE);
                player2Connection.send(actionMessage);

            }else{
                TurnUpdateMessage turnMessage = new TurnUpdateMessage(player1.getUsername(), player1.getColor());

                player1Connection.send(turnMessage);
                player2Connection.send(turnMessage);

                ActionsUpdateMessage actionMessage = new ActionsUpdateMessage();
                actionMessage.addAction(Actions.DECK);
                player1Connection.send(actionMessage);

            }


        }else{

            ClientConnection player3Connection = playingConnection.get(keys.get(2));

            Game model = new Game(keys.get(0), keys.get(1), keys.get(2), board, simple);
            Controller controller = new Controller(model);

            Player player1 = model.getPlayers().get(0);
            View player1View = new RemoteView(player1, player1Connection);

            player1.addObserver(player1View);
            player1.setSession(model);
            board.addObserver(player1View);
//            controller.addObserver(player1View);
            player1View.addObserver(controller);
            model.addObserver(player1View);


            Player player2 = model.getPlayers().get(1);
            View player2View = new RemoteView(player2, player2Connection);

            player2.addObserver(player2View);
            player2.setSession(model);
            board.addObserver(player2View);
//            controller.addObserver(player2View);
            player2View.addObserver(controller);
            model.addObserver(player2View);

            Player player3 = model.getPlayers().get(2);
            View player3View = new RemoteView(player3, player3Connection);

            player3.addObserver(player3View);
            player3.setSession(model);
            board.addObserver(player3View);
//            controller.addObserver(player3View);
            player3View.addObserver(controller);
            model.addObserver(player3View);

            this.addObserver(controller);




            if(model.isSimple()){
                TurnUpdateMessage turnMessage = new TurnUpdateMessage(player2.getUsername(), player2.getColor());

                player1Connection.send(turnMessage);
                player2Connection.send(turnMessage);
                player3Connection.send(turnMessage);

                ActionsUpdateMessage actionMessage = new ActionsUpdateMessage();
                actionMessage.addAction(Actions.PLACE);
                player2Connection.send(actionMessage);

            }else{
                TurnUpdateMessage turnMessage = new TurnUpdateMessage(player1.getUsername(), player1.getColor());

                player1Connection.send(turnMessage);
                player2Connection.send(turnMessage);
                player3Connection.send(turnMessage);

                ActionsUpdateMessage actionMessage = new ActionsUpdateMessage();
                actionMessage.addAction(Actions.DECK);
                player1Connection.send(actionMessage);

            }
        }
    }
}
