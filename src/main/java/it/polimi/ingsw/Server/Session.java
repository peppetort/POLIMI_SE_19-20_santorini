package it.polimi.ingsw.Server;



import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    public int participant;
    public boolean simple;
    public Server server;
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();


    Session(ClientConnection creatorConnection,String username,int p,boolean simple,Server server){
        participant=p;
        this.simple=simple;
        waitingConnection.put(username,creatorConnection);
        this.server=server;
        creatorConnection.asyncSend("Wait participants");
    }
    public void addParticipant(ClientConnection Connection,String username,String game)
    {
        waitingConnection.put(username,Connection);
        if(waitingConnection.size()==participant)
        {
            server.disponibleSession.remove(game);
            this.lobby();
        }

    }
    public void lobby(){

        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        ClientConnection c1 = waitingConnection.get(keys.get(0));
        ClientConnection c2 = waitingConnection.get(keys.get(1));

        Board board=new Board();
        Game model = new Game(keys.get(0),keys.get(1),board,false);
        Player player1 = new Player(keys.get(0),model);
        Player player2 = new Player(keys.get(1),model);
        View player1View = new RemoteView(player1, keys.get(1), c1);
        View player2View = new RemoteView(player2, keys.get(0), c2);

        Controller controller = new Controller(model);
        //model.addObserver(player1View);
       // model.addObserver(player2View);
        player1View.addObserver(controller);
        player2View.addObserver(controller);
        playingConnection.put(c1, c2);
        playingConnection.put(c2, c1);
        waitingConnection.clear();

        if(model.isSimple())
        {
            c1.asyncSend("starting\n");
            c1.asyncSend(board.stamp());
            c1.asyncSend("put your worker\n");
            c2.asyncSend("starting\n");
            c2.asyncSend(board.stamp());
        }
        else
        {
            String god="";
            for( God g :God.values())
                god+=g+"-";
            c1.asyncSend("choose the card");
            c1.asyncSend(god);
        }
        c2.asyncSend("wait\n");
    }

}
