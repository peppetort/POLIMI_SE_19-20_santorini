package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.SimpleGameException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Observer.Observable;

import java.util.ArrayList;

/**
 * Classe che rappresenta la partita stessa.
 */
public class Game extends Observable<Message> {
    private final ArrayList<Player> players = new ArrayList<>();    //lista dei player
    private ArrayList<Card> cards;          //list delle carte selezionate dai player
    private final Board board;
    private final boolean simpleGame;

    /**
     * Rappresenta il costruttore della classe {@link Game} nel caso in cui la partita sia per due giocatori.
     *
     * @param player1    Giocatore1 (lo sfidante)
     * @param player2    Giocatore2
     * @param board      La tavola su cui si svolge la partita
     * @param simpleGame se true la partita è senza carte, altrimenti i {@link Player} avranno la loro {@link Card} ciascuno
     */

    public Game(String player1, String player2, Board board, boolean simpleGame) {
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1,this,Color.BLUE));
        players.add(new Player(player2,this,Color.RED));
    }

    /**
     * Rappresenta il costruttore della classe {@link Game} nel caso in cui la partita sia per tre giocatori.
     *
     * @param player1    Giocatore1 (lo sfidante)
     * @param player2    Giocatore2
     * @param board      La tavola su cui si svolge la partita
     * @param simpleGame se true la partita è senza carte, altrimenti i {@link Player} avranno la loro {@link Card} ciascuno
     */
    public Game(String player1, String player2, String player3, Board board, boolean simpleGame) {
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1,this,Color.BLUE));
        players.add(new Player(player2,this,Color.RED));
        players.add(new Player(player3,this,Color.GREEN));
    }


/*    public void addCard(Card card){
        if(cards.size() < players.size()) {
            if(!simpleGame) {
                for(Card c: cards){
                    if(c.getName()==card.getName()){
                        throw new DuplicateCardException("Card already chosen!");
                    }
                }
                cards.add(card);
            }else{throw new SimpleGameException("Game mode: no cards!");}
        }
        else{
            throw new RuntimeException("non si possono aggiungere ulteriori carte");
        }
    }*/

    public void addCards(ArrayList<Card> cards){
        if(simpleGame){
            throw new SimpleGameException("Game mode: no cards!");
        }
        this.cards = cards;
    }

    /**
     * @return un ArrayList contenente tutte le {@link Card} scelte dal giocatore sfidante
     */
    public ArrayList<Card> getCards(){
        ArrayList<Card> clonedCards = new ArrayList<>(cards.size());
        clonedCards.addAll(cards);
        return clonedCards;
    }
    /**
     *
     * @return un ArrayList contenente i {@link Player} della partita
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }
    /**
     * @return True se il gioco è in modalità semplice (senza carte)
     */
    public boolean isSimple(){return this.simpleGame;}

    /**
     * @return la {@link Board} su cui si svolge la partita
     */
    public Board getBoard() {
        return board;
    }

    //TODO: controllare se serve
    public void removePlayer(Player player) throws NullPointerException, IndexOutOfBoundsException {
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();

        board.getBox(worker1.getXPos(), worker1.getYPos()).removePawn();
        board.getBox(worker2.getXPos(), worker2.getYPos()).removePawn();
        players.remove(player);

    }
}
