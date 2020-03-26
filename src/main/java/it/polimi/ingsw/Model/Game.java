package it.polimi.ingsw.Model;

import java.util.ArrayList;

/**
 * Classe che rappresenta la partita stessa.
 */
public class Game {
    private long matchID;
    private ArrayList<Player> players = new ArrayList<Player>();    //lista dei player
    private ArrayList<Card> deck = new ArrayList<Card>();           //lista di tutte le carte divinità disponibili
    private ArrayList<Card> cards = new ArrayList<Card>();          //list delle carte selezionate dai player
    private Board board;
    private boolean simpleGame;

    /**
     * Rappresenta il costruttore della classe {@link Game} nel caso in cui la partita sia per due giocatori.
     * @param player1 Giocatore1 (lo sfidante)
     * @param player2 Giocatore2
     * @param board La tavola su cui si svolge la partita
     * @param simpleGame se true la partita è senza carte, altrimenti i {@link Player} avranno la loro {@link Card} ciascuno
     */

    public Game(String player1, String player2,Board board,boolean simpleGame){
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1,this));
        players.add(new Player(player2,this));
        for(God g: God.values()){
            deck.add(new Card(g));
        }
    }
    /**
     * Rappresenta il costruttore della classe {@link Game} nel caso in cui la partita sia per tre giocatori.
     * @param player1 Giocatore1 (lo sfidante)
     * @param player2 Giocatore2
     * @param board La tavola su cui si svolge la partita
     * @param simpleGame se true la partita è senza carte, altrimenti i {@link Player} avranno la loro {@link Card} ciascuno
     */
    public Game(String player1,String player2,String player3,Board board,boolean simpleGame){
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1,this));
        players.add(new Player(player2,this));
        players.add(new Player(player3,this));
        for(God g: God.values()){
            deck.add(new Card(g));
        }
    }
    /**
     * @return l'ID del match
     */
    public long getMatchID() {
        return matchID;
    }
    /**
     * @param matchID ID del match
     */
    public void setMatchID(long matchID) {
        this.matchID = matchID;
    }
    /**
     * @param card rappresenta la {@link Card} che player1 vuole aggiungere alle carte utilizzabili in gioco.
     * @throws RuntimeException se provo ad inserire una carta già presente
     * @throws RuntimeException se provo ad inserire più carte che numero di player
     * @throws RuntimeException se provo ad inserire una carta quando il gioco è semplice (senza l'utilizzo di carte)
     */
    public void addCard(Card card){
        if(cards.size() < players.size()) {
            if(!simpleGame) {
                for(Card c: cards){
                    if(c.getName()==card.getName()){
                        throw new RuntimeException("Carta già inserita");
                    }
                }
                cards.add(card);
            }else{throw new RuntimeException("Gioco senza Carte");}
        }
        else{
            throw new RuntimeException("non si possono aggiungere ulteriori carte");
        }
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
}
