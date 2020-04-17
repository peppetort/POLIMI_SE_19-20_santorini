package it.polimi.ingsw.Server;


import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Exceptions.FullSessionException;
import it.polimi.ingsw.Model.Board;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.View.RemoteView;
import it.polimi.ingsw.View.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session extends Observable<Message> {
    private String name;
    private int participant;
    private boolean simple;
    private Server server;
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<String, ClientConnection> playingConnection = new HashMap<>();


    public Session(ClientConnection creatorConnection, int p, boolean simple, Server server, String sessionName) {
        this.participant = p;
        this.simple = simple;
        this.server = server;
        this.name = sessionName;
        addParticipant(creatorConnection);
    }

    public synchronized void addParticipant(ClientConnection player) {
        if (waitingConnection.size() < participant) {
            waitingConnection.put(player.getUsername(), player);

            if (waitingConnection.size() == participant) {
                server.disponibleSession.remove(name);
                this.start();
            } else {
                player.asyncSend("Wait participants");
            }
        } else {
            throw new FullSessionException("Session is full!");
        }

    }

    public synchronized void deregisterConnection(String username) {
        if (playingConnection.isEmpty()) {
            waitingConnection.remove(username);
        } else {
            //TODO: inviare messaggio al controller per invocare removePlayer() in game
            //TODO: notify(messaggio)
        }
    }


    private void start() {

        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        Board board = new Board();

        //TODO: riempire mappa playingConnection

        if (participant == 2) {
            ClientConnection player1Connection = waitingConnection.get(keys.get(0));
            ClientConnection player2Connection = waitingConnection.get(keys.get(1));

            Game model = new Game(keys.get(0), keys.get(1), board, simple);
            Controller controller = new Controller(model);

            Player player1 = model.getPlayers().get(0);
            Player player2 = model.getPlayers().get(1);

            View player1View = new RemoteView(player1, player2, player1Connection);
            View player2View = new RemoteView(player2, player1, player2Connection);

            player1View.addObserver(controller);
            player2View.addObserver(controller);

            model.addObserver(player1View);
            model.addObserver(player2View);



            this.addObserver(controller);

        } else if (participant == 3) {
            ClientConnection player1Connection = waitingConnection.get(keys.get(0));
            ClientConnection player2Connection = waitingConnection.get(keys.get(1));
            ClientConnection player3Connection = waitingConnection.get(keys.get(2));

            Game model = new Game(keys.get(0), keys.get(1), keys.get(2), board, simple);
            Controller controller = new Controller(model);

            Player player1 = model.getPlayers().get(0);
            Player player2 = model.getPlayers().get(1);
            Player player3 = model.getPlayers().get(2);

            View player1View = new RemoteView(player1, player1, player1Connection);
            View player2View = new RemoteView(player2, player2, player2Connection);
            View player3View = new RemoteView(player3, player3, player3Connection);

            player1View.addObserver(controller);
            player2View.addObserver(controller);
            player3View.addObserver(controller);

            model.addObserver(player1View);
            model.addObserver(player2View);
            model.addObserver(player3View);

            this.addObserver(controller);
        }

        //TODO:gestire la scelta della carte e l'inizio del gioco


/*        if (model.isSimple()) {
            c1.asyncSend("starting\n");
            c1.asyncSend(board.data());
            c1.asyncSend("put your worker\n");
            c2.asyncSend("starting\n");
            c2.asyncSend(board.data());
        } else {
            String god = "";
            for (God g : God.values())
                god += g + "-";
            c1.asyncSend("choose the card");
            c1.asyncSend(god);
        }
        c2.asyncSend("wait\n");
    }*/

    }
}
