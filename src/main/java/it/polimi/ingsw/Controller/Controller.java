package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Controller extends Observable<Message> implements Observer<Message> {

    private final Game game;
    private final ArrayList<Player> playersList;
    private final ArrayList<Card> cards = new ArrayList<>();
    private final HashMap<Player, Boolean> turn = new HashMap<>();
    private final HashMap<Player, Boolean> outcome = new HashMap<>();
    private boolean cardChoiceTurn;
    private boolean firstTurn;
    private int loosingPlayers;

    public Controller(Game game) {
        this.game = game;
        playersList = game.getPlayers();
        loosingPlayers = 0;

        if (game.isSimple()) {
            cardChoiceTurn = false;
            firstTurn = true;
        } else {
            cardChoiceTurn = true;
            firstTurn = false;
        }

        for (Player p : game.getPlayers()) {
            turn.put(p, false);
            outcome.put(p, null);
        }
        turn.replace(game.getPlayers().get(1), true);
    }

    private void updateTurn() {
        int index;

        for (Player p : playersList) {
            if (turn.get(p)) {
                turn.replace(p, false);
                index = (playersList.indexOf(p) + 1) % (playersList.size());
                if (index == 1 && cardChoiceTurn) {
                    cardChoiceTurn = false;
                    firstTurn = true;
                } else if (index == 1 && firstTurn) {
                    firstTurn = false;
                    playersList.get(index).getPlayerMenu().replace("start", true);
                }else {
                    playersList.get(index).getPlayerMenu().replace("start", true);
                }
                if(playersList.size() == loosingPlayers+1){
                    if(outcome.get(playersList.get(index))==null){
                        outcome.replace(playersList.get(index), true);
                    }
                }
                turn.replace(playersList.get(index), true);
                break;
            }
        }

        if(outcome.size() == 1){
            outcome.replaceAll((key, value) -> true);
        }
    }

    public HashMap<Player, Boolean> getTurn() {
        return turn;
    }

    public HashMap<Player, Boolean> getOutcome() {
        return outcome;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    //Actions
    private void performStart(PlayerStart message) {
        Player player = message.getPlayer();
        Worker worker = message.getWorker();

        if (turn.get(player) && !firstTurn && outcome.get(player) == null) {

            if (outcome.get(player) == null && loosingPlayers == playersList.size() - 1) {
                outcome.replace(player, true);
            }

            try {
                player.getTurn().start(worker);
            } catch (PlayerLostException e1) {
                outcome.replace(player, false);
                loosingPlayers++;
                updateTurn();
            } catch (RuntimeException e2) {
                e2.printStackTrace();
            }
        }

    }

    private void performMove(PlayerMove message) {
        Player player = message.getPlayer();
        Turn playerTurn = player.getTurn();
        int x = message.getX();
        int y = message.getY();

        if (turn.get(player) && !firstTurn && outcome.get(player) == null) {
            try {
                playerTurn.move(x, y);
                //TODO: ??
                game.notify(new BoardUpdate(game.getBoard().data(), game.getPlayers()));
                if (playerTurn.won()) {
                    outcome.replaceAll((key, value) -> false);
                    outcome.replace(player, true);
                    loosingPlayers = playersList.size() - 1;
                }
            } catch (IndexOutOfBoundsException e1) {
                //TODO: notificare al giocatore
            } catch (InvalidMoveException e2) {
                //TODO: notificare al giocatore
            } catch (AthenaGoUpException e3) {
                //TODO: notificare al giocatore
            } catch (RuntimeException e4) {
                e4.printStackTrace();
            }
        }
    }

    private void performBuild(PlayerBuild message) {
        Player player = message.getPlayer();
        Turn playerTurn = player.getTurn();
        int x = message.getX();
        int y = message.getY();

        if (turn.get(player) && !firstTurn && outcome.get(player) == null) {
            try {
                playerTurn.build(x, y);
                //TODO: ??
                game.notify(new BoardUpdate(game.getBoard().data(), game.getPlayers()));
            } catch (IndexOutOfBoundsException e1) {
                //TODO: notificare al giocatore
            } catch (InvalidBuildException e2) {
                //TODO: notificare al giocatore
            } catch (RuntimeException e3) {
                e3.printStackTrace();
            }
        }
    }

    private void performEnd(PlayerEnd message) {
        Player player = message.getPlayer();
        Turn playerTurn = player.getTurn();

        if (turn.get(player) && !firstTurn && outcome.get(player) == null) {
            try {
                playerTurn.end();
                updateTurn();
            } catch (RuntimeException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void performDeckBuilding(DeckChoice message) {
        Player player = message.getPlayer();
        Set<String> sCards = message.getCards();

        if (cardChoiceTurn && player.equals(playersList.get(0))) {
            if (sCards.size() == playersList.size()) {
                try {
                    for (String c : sCards) {
                        Card card = new Card(God.valueOf(c));
                        cards.add(card);
                    }
                    game.addCards(cards);
                } catch (SimpleGameException e1) {
                    e1.printStackTrace();
                } catch (IllegalArgumentException e2) {
                    cards.clear();
                    //TODO: nome inserito non valido -> notificare scelta invalida
                }

            }
        }
    }

    private void performCardChoice(CardChoice message) {
        Player player = message.getPlayer();
        String cardName = message.getCard();

        if (cardChoiceTurn && turn.get(player)) {
            try {
                Card card = null;
                for (Card c : cards) {
                    if (c.getName().equals(God.valueOf(cardName))) {
                        card = c;
                    }
                }
                player.setCard(card);
                cards.remove(card);
                updateTurn();
            } catch (SimpleGameException | CardAlreadySetException e1) {
                e1.printStackTrace();
            } catch (NullPointerException e2) {
                //TODO: carta non presente nel deck -> notificare scelta invalida
            } catch (IllegalArgumentException e3) {
                //TODO: nome scelto inesistente -> notificare scelta invalida
            }
        }
    }

    private void performPawnPositioning(PlacePawn message) {
        Player player = message.getPlayer();
        int worker1X = message.getX1();
        int worker1Y = message.getY1();
        int worker2X = message.getX2();
        int worker2Y = message.getY2();
        Board board = game.getBoard();

        if (turn.get(player) && firstTurn) {
            try {
                board.placePawn(player.getWorker1(), worker1X, worker1Y);
                board.placePawn(player.getWorker2(), worker2X, worker2Y);
                updateTurn();
            } catch (IndexOutOfBoundsException e1) {
                //TODO: notificare scelta invalida
            }
            //TODO: ??
            game.notify(new BoardUpdate(board.data(), game.getPlayers()));
        }
    }


    private void performPlayerRemoval(PlayerRemove message) {
        Player player = message.getPlayer();
        Board board = game.getBoard();
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();

        outcome.remove(player);

        if (turn.get(player)) {
            updateTurn();
        }

        board.getBox(worker1.getXPos(), worker1.getYPos()).removePawn();
        board.getBox(worker2.getXPos(), worker2.getYPos()).removePawn();

        playersList.remove(player);
        turn.remove(player);

    }


    @Override
    public void update(Message message) {

        if (message instanceof PlayerMove) {
            performMove((PlayerMove) message);
        }
        if (message instanceof PlayerBuild) {
            performBuild((PlayerBuild) message);
        }
        if (message instanceof CardChoice) {
            performCardChoice((CardChoice) message);
        }
        if (message instanceof DeckChoice) {
            performDeckBuilding((DeckChoice) message);
        }
        if (message instanceof PlayerStart) {
            performStart((PlayerStart) message);
        }
        if (message instanceof PlayerEnd) {
            performEnd((PlayerEnd) message);
        }
        if (message instanceof PlayerRemove) {
            performPlayerRemoval((PlayerRemove) message);
        }
        if (message instanceof PlacePawn) {
            performPawnPositioning((PlacePawn) message);
        }
    }
}
