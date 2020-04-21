package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.BoardUpdate;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;

import java.util.ArrayList;
import java.util.HashMap;

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
                    if(card.length == 2){
                        handleDeck(card[0], card[1]);
                    }else {
                        handleDeck(card[0], card[1], card[2]);
                    }
                } else if (inputs[0].compareTo("CARD") == 0) {
                    handleCard(inputs[1]);
                } else if (inputs[0].compareTo("MOVE") == 0) {
                    handleMove(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));
                } else if (inputs[0].compareTo("BUILD") == 0) {
                    handleBuild(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]));
                } else if (inputs[0].compareTo("END") == 0) {
                    handleEnd();
                } else if (inputs[0].compareTo("START") == 0) {
                    handleStart(Integer.parseInt(inputs[1]));
                } else if (inputs[0].compareTo("PLACE") == 0) {
                    handlePlacing(Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]), Integer.parseInt(inputs[3]), Integer.parseInt(inputs[4]));
                } else if (inputs[0].compareTo("HELP") == 0) {
                    String placeHelpMessage = "place [x1] [y1] [x2] [y2]: places pawns on the board\n";
                    String startHelpMessage = "start [1]/[2]: starts the game turn with one of the two workers\n";
                    String moveHelpMessage = "move [x] [y]: moves pawn specified in the start command\n";
                    String buildHelpMessage = "build [x] [y]: builds a level above the existing one\n";
                    String endHelpMessage = "end: ends turn\n";
                    String deckBuildHelpMessage = "deck [card1],[card2]{,[card3]}: makes up the game deck\n";
                    String chooseCardHelpMessage = "card [card]: selects a card from those available on the deck\n";
                    String buildDomeHelpMessage = "dome [x] [y]: builds a dome\n";

                    StringBuilder helpMessage = new StringBuilder();

                    if (player.getPlayerMenu().get("placePawns")) {
                        helpMessage.append(placeHelpMessage);
                    }
                    if (player.getPlayerMenu().get("buildDeck")) {
                        helpMessage.append(deckBuildHelpMessage);
                    }
                    if (player.getPlayerMenu().get("chooseCard")) {
                        helpMessage.append(chooseCardHelpMessage);
                    }
                    if (player.getPlayerMenu().get("start")) {
                        helpMessage.append(startHelpMessage);
                    }
                    if (player.getPlayerMenu().get("move")) {
                        helpMessage.append(moveHelpMessage);
                    }
                    if (player.getPlayerMenu().get("build")) {
                        helpMessage.append(buildHelpMessage);
                    }
                    if (player.getPlayerMenu().get("end")) {
                        helpMessage.append(endHelpMessage);
                    }
                    //TODO: atlas
/*                    if (player.getPlayerMenu().get("dome")) {
                        helpMessage.append(buildDomeHelpMessage);
                    }*/

                    if (helpMessage.length() == 0) {
                        helpMessage.append("Wait you turn");
                    }

                    clientConnection.asyncSend(helpMessage.toString());
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                clientConnection.asyncSend("Input Error! Please try again");
            } catch (Exception e) {
                clientConnection.asyncSend(e.getMessage());
            }
        }
    }

    private final ClientConnection clientConnection;

    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
        ArrayList<Player> opponents = player.getSession().getPlayers();
        HashMap<String, Boolean> playerMenu = player.getPlayerMenu();
        StringBuilder opponentsMessage = new StringBuilder("Your opponents are: ");

        for (Player p : opponents) {
            String opponentUsername = p.getUsername();
            if (!opponentUsername.equals(player.getUsername())) {
                opponentsMessage.append(opponentUsername).append(" ");
            }
        }
        c.asyncSend(opponentsMessage.toString());

        if (!playerMenu.get("buildDeck") && !playerMenu.get("placePawns")) {
            c.asyncSend("Waiting...");
        }
    }

    @Override
    public void update(Message message) {
        if (message instanceof BoardUpdate) {
            clientConnection.asyncSend(message);
        } else {
            System.err.println("Malformed message: " + message.toString());
        }

    }


    @Override
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }
}
