package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class Game {
    private long matchID;
    private ArrayList<Player> players = new ArrayList<Player>();    //lista dei player
    private ArrayList<Card> deck = new ArrayList<Card>();           //lista di tutte le carte divinità disponibili
    private ArrayList<Card> cards = new ArrayList<Card>();          //list delle carte selezionate dai player
    private Board board;
    private boolean simpleGame;


    public Game(String player1, String player2,Board board,boolean simpleGame){
        this.board = board;
        this.simpleGame = simpleGame;
        players.add(new Player(player1,this));
        players.add(new Player(player2,this));
        for(God g: God.values()){
            deck.add(new Card(g));
        }
    }
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

    public long getMatchID() {
        return matchID;
    }


    public void setMatchID(long matchID) {
        this.matchID = matchID;
    }


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


    public ArrayList<Card> getCards(){
        ArrayList<Card> clonedCards = new ArrayList<>(cards.size());
        clonedCards.addAll(cards);
        return clonedCards;
    }
    public ArrayList<Player> getPlayers(){
        ArrayList<Player> clonedPlayers = new ArrayList<>(players.size());
        clonedPlayers.addAll(players);
        return clonedPlayers;
    }


    public boolean isSimple(){return this.simpleGame;}

    public Board getBoard() {
        return board;
    }
}
