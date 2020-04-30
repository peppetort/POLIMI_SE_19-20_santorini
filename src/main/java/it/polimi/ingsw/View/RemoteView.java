package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;

import java.util.ArrayList;

public class RemoteView extends View {


    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            try {
                message = message.toUpperCase();

                String[] inputs = message.split(" ");
                String[] card;

                if (inputs[0].compareTo("DECK") == 0) {
                    card = inputs[1].split(",");
                    if (card.length == 2) {
                        handleDeck(card[0], card[1]);
                    } else {
                        handleDeck(card[0], card[1], card[2]);
                    }
                } else if (inputs[0].compareTo("CARD") == 0) {
                    handleCard(inputs[1]);
                } else if (inputs[0].compareTo("MOVE") == 0) {
                    handleMove(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));
                } else if (inputs[0].compareTo("BUILD") == 0) {
                    if (inputs[1].compareTo("DOME") == 0) {
                        if (player.getCard().getName().equals(God.ATLAS)) {
                            handleBuildDome(Integer.parseInt(inputs[2]), Integer.parseInt(inputs[3]));
                        }else {
                            throw new IllegalArgumentException("Can't build a dome directly");
                        }
                    } else {
                        handleBuild(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));
                    }
                } else if (inputs[0].compareTo("END") == 0) {
                    handleEnd();
                } else if (inputs[0].compareTo("START") == 0) {
                    handleStart(Integer.parseInt(inputs[1]));
                } else if (inputs[0].compareTo("PLACE") == 0) {
                    handlePlacing(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]), Integer.parseInt(inputs[3]), Integer.parseInt(inputs[4]));
                } else if (inputs[0].compareTo("UNDO") == 0) {
                    handleUndo();
                }
                else {
                    throw new IllegalArgumentException();
                }

            } catch (IllegalArgumentException e) {

                clientConnection.send("Input Error! Please try again");
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
        } else if(message instanceof BoardUndoMessage){
            clientConnection.send(message);
        }else {
            System.err.println("Malformed message: " + message.toString());
        }
    }

}
