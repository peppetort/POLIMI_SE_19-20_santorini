package it.polimi.ingsw.Server;



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

        c1.asyncSend("la partita inizia1");
        c2.asyncSend("la partita inizia2");

    };
}
