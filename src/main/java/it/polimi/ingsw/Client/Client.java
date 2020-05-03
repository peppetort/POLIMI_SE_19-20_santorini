package it.polimi.ingsw.Client;

import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Observable implements Observer<Object> {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Object inputObject;
    private Object outputObject;

    private String ip;
    private int port;

    private ClientStatus status;
    private ClientBoard board;

    private CLI cli;

    public Client(String ip,int port){
        this.ip = ip;
        this.port = port;
    }

    public void startClient() throws IOException,ClassNotFoundException {
        socket = new Socket(ip,port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        cli = new CLI();
        cli.addObserver(this);
        this.addObserver(cli);
        notify(0);

        Thread reader = asyncReadFromSocket();
        reader.run();

    }

    @Override
    public void update(Object message) {
        send(message);
    }

    public Thread asyncReadFromSocket(){
        Thread reader = new Thread(() -> {
            Object inputObject;
            while(socket.isConnected()){
                try {
                    inputObject = in.readObject();
                    if(inputObject instanceof String || inputObject instanceof SessionListMessage){
                        notify(inputObject);
                    }else if (inputObject instanceof Exception) {
                        System.out.println(((Exception) inputObject).getMessage());
                    } else if (inputObject instanceof ClientInitMessage) {
                        String username = ((ClientInitMessage) inputObject).getUsername();
                        ArrayList<Color> players = ((ClientInitMessage) inputObject).getPlayers();
                        status = new ClientStatus(username, players.get(0));
                        board = new ClientBoard(players);
                        cli.setClientBoard(board);
                        cli.setClientStatus(status);
                        status.addObserver(cli);
                        board.addObserver(cli);
                    } else if (inputObject instanceof TurnUpdateMessage) {
                        String username = ((TurnUpdateMessage) inputObject).getUsername();
                        status.updateTurn(username);
                    } else if (inputObject instanceof ActionsUpdateMessage) {
                        ArrayList<String> actions = ((ActionsUpdateMessage) inputObject).getActions();
                        status.updateAction(actions);
                    } else if (inputObject instanceof CardUpdateMessage) {
                        God card = ((CardUpdateMessage) inputObject).getCard();
                        status.setCard(card);
                    } else if (inputObject instanceof BoardUpdatePlaceMessage) {
                        int x = ((BoardUpdatePlaceMessage) inputObject).getX();
                        int y = ((BoardUpdatePlaceMessage) inputObject).getY();
                        Color player = ((BoardUpdatePlaceMessage) inputObject).getPlayer();
                        int worker = ((BoardUpdatePlaceMessage) inputObject).getWorker();
                        board.placePlayer(x, y, player, worker);

                    } else if (inputObject instanceof BoardUpdateBuildMessage) {
                        int x = ((BoardUpdateBuildMessage) inputObject).getX();
                        int y = ((BoardUpdateBuildMessage) inputObject).getY();
                        int level = ((BoardUpdateBuildMessage) inputObject).getLevel();
                        board.setLevel(x, y, level);

                    }else if(inputObject instanceof WinMessage){
                        String winUser = ((WinMessage) inputObject).getUsername();
                        status.setWinner(winUser);
                    }else if (inputObject instanceof LostMessage){
                        String loser = ((LostMessage) inputObject).getUsername();
                        Color loserColor = ((LostMessage) inputObject).getColor();
                        status.lose(loser);
                        board.lose(loserColor);
                    }else if(inputObject instanceof DeckUpdateMessage){
                        ArrayList<God> deck = ((DeckUpdateMessage) inputObject).getDeck();
                        status.updateDeck(deck);
                    }else if (inputObject instanceof BoardUndoMessage) {
                        int x = ((BoardUndoMessage) inputObject).getX();
                        int y = ((BoardUndoMessage) inputObject).getY();
                        Color player = ((BoardUndoMessage) inputObject).getPlayer();
                        Integer worker = ((BoardUndoMessage) inputObject).getWorker();
                        int level=((BoardUndoMessage) inputObject).getLevel();
                        // ristabilisce la visione della board all'inizio del turno
                        board.restore(x,y,worker,player,level);
                        if(worker!=null) {
                            board.placePlayer(x, y, player, worker);
                            notify(1);
                        }
                        else notify(1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return reader;
    }

    public void send(Object message) {
        synchronized (out) {
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            }catch(Exception e){}
        }
    }


}