package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.SimpleGameException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Observer.Observable;

import java.util.ArrayList;

/**
 * Class which represents the model itself
 */
public class Game extends Observable<Message> {
    private final ArrayList<Player> players = new ArrayList<>();    //lista dei player
    private ArrayList<Card> cards;          //list delle carte selezionate dai player
    private final Board board;
    private final boolean simpleGame;


    /**
     * Constructor of the class {@link Game} if it's a 2-Player's match
     *
     * @param player1    Player 1, the one which choose the deck
     * @param player2    Player 2
     * @param board      Board where the game is taken
     * @param simpleGame true if the game is without any {@link Card}, false if each {@link Player} has a {@link Card}
     */

    public Game(String player1, String player2, Board board, boolean simpleGame) {
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1, this, Color.BLUE));
        players.add(new Player(player2, this, Color.RED));
    }

    /**
     * Constructor of the class {@link Game} if it's a 3-Player's match
     *
     * @param player1    Player 1, the one which choose the deck
     * @param player2    Player 2
     * @param player3    Player 3
     * @param board      Board where the game is taken
     * @param simpleGame true if the game is without any {@link Card}, false if each {@link Player} has a {@link Card}
     */
    public Game(String player1, String player2, String player3, Board board, boolean simpleGame) {
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1, this, Color.BLUE));
        players.add(new Player(player2, this, Color.RED));
        players.add(new Player(player3, this, Color.GREEN));
    }

    public void addCards(ArrayList<Card> cards) {
        if (simpleGame) {
            throw new SimpleGameException("Game mode: no cards!");
        }
        this.cards = cards;
    }


    public ArrayList<Card> getCards() {
        ArrayList<Card> clonedCards = new ArrayList<>(cards.size());
        clonedCards.addAll(cards);
        return clonedCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isSimple() {
        return this.simpleGame;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * This method will remove the parameter {@link Player} from the {@link Game} and his {@link Worker} from the {@link Board}
     *
     * @param player
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     */
    public void removePlayer(Player player) throws NullPointerException, IndexOutOfBoundsException {
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();

        board.getBox(worker1.getXPos(), worker1.getYPos()).removePawn();
        board.getBox(worker2.getXPos(), worker2.getYPos()).removePawn();
        players.remove(player);
    }


}
