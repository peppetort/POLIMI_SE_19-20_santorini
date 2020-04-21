package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Exceptions.CardAlreadySetException;
import it.polimi.ingsw.Exceptions.PlayerLostException;
import it.polimi.ingsw.Exceptions.SimpleGameException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


//public class Controller extends Observable<Message> implements Observer<Message> {
//
//    private final Game game;
//    private final ArrayList<Player> playersList;
//    private final ArrayList<Card> cards = new ArrayList<>();
//    private final HashMap<Player, Boolean> turn = new HashMap<>();
//    private final HashMap<Player, Boolean> outcome = new HashMap<>();
//    private int loosingPlayers;
//
//    public Controller(Game game) {
//        this.game = game;
//        playersList = game.getPlayers();
//        loosingPlayers = 0;
//
//        for (Player p : game.getPlayers()) {
//            turn.put(p, false);
//            outcome.put(p, null);
//        }
//
//        if (game.isSimple()) {
//            playersList.get(1).getPlayerMenu().replace("placePawns", true);
//            turn.replace(game.getPlayers().get(1), true);
//        } else {
//            playersList.get(0).getPlayerMenu().replace("buildDeck", true);
//            turn.replace(game.getPlayers().get(0), true);
//        }
//
//    }
//
//    private void updateTurn() {
//        int nextPlayerIndex;
//        Player nextPlayer;
//        HashMap<String, Boolean> playerMenu;
//        HashMap<String, Boolean> nextPlayerMenu;
//
//        for (Player player : playersList) {
//            if (turn.get(player)) {
//                playerMenu = player.getPlayerMenu();
//                nextPlayerIndex = (playersList.indexOf(player) + 1) % (playersList.size());
//                nextPlayer = playersList.get(nextPlayerIndex);
//                nextPlayerMenu = nextPlayer.getPlayerMenu();
//
//                //Finito il giro
//                if (nextPlayerIndex == 1) {
//                    if (playerMenu.get("buildDeck")) {
//                        playerMenu.replace("buildDeck", false);
//                        nextPlayerMenu.replace("chooseCard", true);
//                    } else if (playerMenu.get("chooseCard")) {
//                        playerMenu.replace("chooseCard", false);
//                        nextPlayerMenu.replace("placePawns", true);
//                    } else if (playerMenu.get("placePawns")) {
//                        playerMenu.replace("placePawns", false);
//                        nextPlayerMenu.replace("start", true);
//                    } else {
//                        nextPlayerMenu.replace("start", true);
//                    }
//                } else {
//                    if (playerMenu.get("chooseCard")) {
//                        playerMenu.replace("chooseCard", false);
//                        nextPlayerMenu.replace("chooseCard", true);
//                    } else if (playerMenu.get("placePawns")) {
//                        playerMenu.replace("placePawns", false);
//                        nextPlayerMenu.replace("placePawns", true);
//                    } else {
//                        nextPlayerMenu.replace("start", true);
//                    }
//                }
//
//                //Altri giocatori hanno perso -> il rimanente vince
//                if (playersList.size() == loosingPlayers + 1 && outcome.get(nextPlayer) == null) {
//                    outcome.replace(nextPlayer, true);
//                }
//
//                turn.replace(player, false);
//                turn.replace(nextPlayer, true);
//                break;
//            }
//
//            //Unico giocatore rimasto vince
//            if (outcome.size() == 1) {
//                outcome.replaceAll((key, value) -> true);
//            }
//        }
//    }
//
//    public HashMap<Player, Boolean> getTurn() {
//        return turn;
//    }
//
//    public HashMap<Player, Boolean> getOutcome() {
//        return outcome;
//    }
//
//    public ArrayList<Card> getCards() {
//        return cards;
//    }
//
//    //Actions
//    private void performStart(PlayerStart message) {
//        Player player = message.getPlayer();
//        Worker worker = message.getWorker();
//
//        if (turn.get(player) && player.getPlayerMenu().get("start") && outcome.get(player) == null) {
//
//            if (outcome.get(player) == null && loosingPlayers == playersList.size() - 1) {
//                outcome.replace(player, true);
//            }
//
//            try {
//                player.getTurn().start(worker);
//            } catch (PlayerLostException e1) {
//                outcome.replace(player, false);
//                loosingPlayers++;
//                updateTurn();
//            } catch (RuntimeException e2) {
//                e2.printStackTrace();
//            }
//        }
//
//    }
//
//    private void performMove(PlayerMove message) throws RuntimeException {
//        Player player = message.getPlayer();
//        Turn playerTurn = player.getTurn();
//        int x = message.getX();
//        int y = message.getY();
//
//        if (turn.get(player) && player.getPlayerMenu().get("move") && outcome.get(player) == null) {
//            playerTurn.move(x, y);
//            if (playerTurn.won()) {
//                outcome.replaceAll((key, value) -> false);
//                outcome.replace(player, true);
//                loosingPlayers = playersList.size() - 1;
//            }
//        }
//    }
//
//    private void performBuild(PlayerBuild message) throws RuntimeException {
//        Player player = message.getPlayer();
//        Turn playerTurn = player.getTurn();
//        int x = message.getX();
//        int y = message.getY();
//
//        if (turn.get(player) && player.getPlayerMenu().get("build") && outcome.get(player) == null) {
//            playerTurn.build(x, y);
//        }
//    }
//
//    private void performEnd(PlayerEnd message) throws RuntimeException {
//        Player player = message.getPlayer();
//        Turn playerTurn = player.getTurn();
//
//        if (turn.get(player) && player.getPlayerMenu().get("end") && outcome.get(player) == null) {
//            playerTurn.end();
//            updateTurn();
//        }
//    }
//
//    private void performDeckBuilding(DeckChoice message) {
//        Player player = message.getPlayer();
//        Set<String> sCards = message.getCards();
//
//        if (turn.get(player) && player.getPlayerMenu().get("buildDeck") && player.equals(playersList.get(0))) {
//            if (sCards.size() == playersList.size()) {
//                try {
//                    for (String c : sCards) {
//                        Card card = new Card(God.valueOf(c));
//                        cards.add(card);
//                    }
//                    game.addCards(cards);
//                    updateTurn();
//                } catch (SimpleGameException e1) {
//                    e1.printStackTrace();
//                } catch (IllegalArgumentException e2) {
//                    cards.clear();
//                    throw new IllegalArgumentException("Card inserted does not exist");
//                }
//
//            }
//        }
//    }
//
//    private void performCardChoice(CardChoice message) throws NullPointerException, IllegalArgumentException {
//        Player player = message.getPlayer();
//        String cardName = message.getCard();
//
//        if (player.getPlayerMenu().get("chooseCard") && turn.get(player)) {
//            try {
//                Card card = null;
//                for (Card c : cards) {
//                    if (c.getName().equals(God.valueOf(cardName))) {
//                        card = c;
//                    }
//                }
//                player.setCard(card);
//                cards.remove(card);
//                updateTurn();
//            } catch (SimpleGameException | CardAlreadySetException e1) {
//                e1.printStackTrace();
//            }
//        }
//    }
//
//    private void performPawnPositioning(PlacePawn message) throws IndexOutOfBoundsException {
//        Player player = message.getPlayer();
//        int worker1X = message.getX1();
//        int worker1Y = message.getY1();
//        int worker2X = message.getX2();
//        int worker2Y = message.getY2();
//        Board board = game.getBoard();
//
//        if (turn.get(player) && player.getPlayerMenu().get("placePawns")) {
//            if (!board.getBox(worker1X, worker1Y).isFree() || !board.getBox(worker2X, worker2Y).isFree()) {
//                throw new RuntimeException("Can't place pawns here! The positions chosen are already occupied");
//            }
//            board.initializePawn(player.getWorker1(), player.getWorker2(),worker1X, worker1Y, worker2X, worker2Y);
//            //board.initializePawn(player.getWorker2(), worker2X, worker2Y);
//            updateTurn();
//        }
//    }
//
//
//    private void performPlayerRemoval(PlayerRemove message) {
//        String playerName = message.getPlayer();
//        Player player = null;
//
//        for (Player p : playersList) {
//            if (p.getUsername().equals(playerName)) {
//                player = p;
//            }
//        }
//
//        if (player != null) {
//            outcome.remove(player);
//            if (turn.get(player)) {
//                if(player.getPlayerMenu().get("buildDeck")){
//                    playersList.remove(player);
//                    turn.remove(player);
//                    Player firstPlayer = playersList.get(0);
//                    firstPlayer.getPlayerMenu().replace("buildDeck", true);
//                    turn.replace(firstPlayer, true);
//                }else{
//                    updateTurn();
//                    playersList.remove(player);
//                    turn.remove(player);
//                }
//            }
//            game.removePlayer(player);
//        }
//
//    }
//
//
//    @Override
//    public void update(Message message) {
//
//        if (message instanceof PlayerMove) {
//            performMove((PlayerMove) message);
//        }
//        if (message instanceof PlayerBuild) {
//            performBuild((PlayerBuild) message);
//        }
//        if (message instanceof CardChoice) {
//            performCardChoice((CardChoice) message);
//        }
//        if (message instanceof DeckChoice) {
//            performDeckBuilding((DeckChoice) message);
//        }
//        if (message instanceof PlayerStart) {
//            performStart((PlayerStart) message);
//        }
//        if (message instanceof PlayerEnd) {
//            performEnd((PlayerEnd) message);
//        }
//        if (message instanceof PlayerRemove) {
//            performPlayerRemoval((PlayerRemove) message);
//        }
//        if (message instanceof PlacePawn) {
//            performPawnPositioning((PlacePawn) message);
//        }
//    }
//}

public class Controller extends Observable<Message> implements Observer<Message> {

    private final Game game;
    private final ArrayList<Player> playersList;
    private final ArrayList<Card> cards = new ArrayList<>();
    private final HashMap<Player, Boolean> turn = new HashMap<>();
    private final HashMap<Player, Boolean> outcome = new HashMap<>();
    private int loosingPlayers;

    public Controller(Game game) {
        this.game = game;
        playersList = game.getPlayers();
        loosingPlayers = 0;

        for (Player p : game.getPlayers()) {
            turn.put(p, false);
            outcome.put(p, null);
        }

        if (game.isSimple()) {
            playersList.get(1).getPlayerMenu().replace("placePawns", true);
            turn.replace(game.getPlayers().get(1), true);
        } else {
            playersList.get(0).getPlayerMenu().replace("buildDeck", true);
            turn.replace(game.getPlayers().get(0), true);
        }
    }

    private void updateTurn() {
        int nextPlayerIndex;
        Player nextPlayer;
        HashMap<String, Boolean> playerMenu;
        HashMap<String, Boolean> nextPlayerMenu;

        for (Player player : playersList) {
            if (turn.get(player)) {
                playerMenu = player.getPlayerMenu();
                nextPlayerIndex = (playersList.indexOf(player) + 1) % (playersList.size());
                nextPlayer = playersList.get(nextPlayerIndex);
                nextPlayerMenu = nextPlayer.getPlayerMenu();

                //Finito il giro
                if (nextPlayerIndex == 1) {
                    if (playerMenu.get("buildDeck")) {
                        playerMenu.replace("buildDeck", false);
                        nextPlayerMenu.replace("chooseCard", true);
                    } else if (playerMenu.get("chooseCard")) {
                        playerMenu.replace("chooseCard", false);
                        nextPlayerMenu.replace("placePawns", true);
                    } else if (playerMenu.get("placePawns")) {
                        playerMenu.replace("placePawns", false);
                        nextPlayerMenu.replace("start", true);
                    } else {
                        nextPlayerMenu.replace("start", true);
                    }
                } else {
                    if (playerMenu.get("chooseCard")) {
                        playerMenu.replace("chooseCard", false);
                        nextPlayerMenu.replace("chooseCard", true);
                    } else if (playerMenu.get("placePawns")) {
                        playerMenu.replace("placePawns", false);
                        nextPlayerMenu.replace("placePawns", true);
                    } else {
                        nextPlayerMenu.replace("start", true);
                    }
                }

                //Altri giocatori hanno perso -> il rimanente vince
                if (playersList.size() == loosingPlayers + 1 && outcome.get(nextPlayer) == null) {
                    outcome.replace(nextPlayer, true);
                }

                turn.replace(player, false);
                turn.replace(nextPlayer, true);
                break;
            }

            //Unico giocatore rimasto vince
            if (outcome.size() == 1) {
                outcome.replaceAll((key, value) -> true);
            }
        }

        game.turnChanged(turn);
    }

    public HashMap<Player, Boolean> getTurn() {
        return turn;
    }

    public HashMap<Player, Boolean> getOutcome() {
        return outcome;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    //Actions
    private void performStart(PlayerStart message) {
        Player player = message.getPlayer();
        Worker worker = message.getWorker();

        if (turn.get(player) && player.getPlayerMenu().get("start") && outcome.get(player) == null) {

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

    private void performMove(PlayerMove message) throws RuntimeException {
        Player player = message.getPlayer();
        Turn playerTurn = player.getTurn();
        int x = message.getX();
        int y = message.getY();

        if (turn.get(player) && player.getPlayerMenu().get("move") && outcome.get(player) == null) {
            playerTurn.move(x, y);
            if (playerTurn.won()) {
                outcome.replaceAll((key, value) -> false);
                outcome.replace(player, true);
                loosingPlayers = playersList.size() - 1;
            }
        }
    }

    private void performBuild(PlayerBuild message) throws RuntimeException {
        Player player = message.getPlayer();
        Turn playerTurn = player.getTurn();
        int x = message.getX();
        int y = message.getY();

        if (turn.get(player) && player.getPlayerMenu().get("build") && outcome.get(player) == null) {
            playerTurn.build(x, y);
        }
    }

    private void performEnd(PlayerEnd message) throws RuntimeException {
        Player player = message.getPlayer();
        Turn playerTurn = player.getTurn();

        if (turn.get(player) && player.getPlayerMenu().get("end") && outcome.get(player) == null) {
            playerTurn.end();
            updateTurn();
        }
    }

    private void performDeckBuilding(DeckChoice message) {
        Player player = message.getPlayer();
        Set<String> sCards = message.getCards();

        if (turn.get(player) && player.getPlayerMenu().get("buildDeck") && player.equals(playersList.get(0))) {
            if (sCards.size() == playersList.size()) {
                try {
                    for (String c : sCards) {
                        Card card = new Card(God.valueOf(c));
                        cards.add(card);
                    }
                    game.addCards(cards);
                    updateTurn();
                } catch (SimpleGameException e1) {
                    e1.printStackTrace();
                } catch (IllegalArgumentException e2) {
                    cards.clear();
                    throw new IllegalArgumentException("Card inserted does not exist");
                }

            }
        }
    }

    private void performCardChoice(CardChoice message) throws NullPointerException, IllegalArgumentException {
        Player player = message.getPlayer();
        String cardName = message.getCard();

        if (player.getPlayerMenu().get("chooseCard") && turn.get(player)) {
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
            }
        }
    }

    private void performPawnPositioning(PlacePawn message) throws IndexOutOfBoundsException {
        Player player = message.getPlayer();
        int worker1X = message.getX1();
        int worker1Y = message.getY1();
        int worker2X = message.getX2();
        int worker2Y = message.getY2();
        Board board = game.getBoard();

        if (turn.get(player) && player.getPlayerMenu().get("placePawns")) {
            if (!board.getBox(worker1X, worker1Y).isFree() || !board.getBox(worker2X, worker2Y).isFree()) {
                throw new RuntimeException("Can't place pawns here! The positions chosen are already occupied");
            }
            board.initializePawn(player.getWorker1(), player.getWorker2(),worker1X, worker1Y, worker2X, worker2Y);
            //board.initializePawn(player.getWorker2(), worker2X, worker2Y);
            updateTurn();
        }
    }


    private void performPlayerRemoval(PlayerRemove message) {
        String playerName = message.getPlayer();
        Player player = null;

        for (Player p : playersList) {
            if (p.getUsername().equals(playerName)) {
                player = p;
            }
        }

        if (player != null) {
            outcome.remove(player);
            if (turn.get(player)) {
                if(player.getPlayerMenu().get("buildDeck")){
                    playersList.remove(player);
                    turn.remove(player);
                    Player firstPlayer = playersList.get(0);
                    firstPlayer.getPlayerMenu().replace("buildDeck", true);
                    turn.replace(firstPlayer, true);
                }else{
                    updateTurn();
                    playersList.remove(player);
                    turn.remove(player);
                }
            }
            game.removePlayer(player);
        }

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

    public void initialize(){
        game.turnChanged(turn);
    }
}
