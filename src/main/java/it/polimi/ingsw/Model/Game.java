package it.polimi.ingsw.Model;

public class Game {
    private long matchID;
    //TODO: ArrayList<Card> Deck
    //TODO: ArrayList<Card> istanze
    private Card card1;
    private Card card2;
    private Card card3;
    //TODO: ArrayList<Player>
    private Player player1;
    private Player player2;
    private Player player3;
    private Board board;
    private boolean simpleGame;


    public Game(String player1, String player2,Board board,boolean simpleGame){
        this.board = board;
        this.simpleGame = simpleGame;
        this.player1 = new Player(player1, this);
        this.player2 = new Player(player2, this);
    }
    public Game(String player1,String player2,String player3,Board board,boolean simpleGame){
        this.board = board;
        this.simpleGame = simpleGame;
        this.player1 = new Player(player1, this);
        this.player2 = new Player(player2, this);
        this.player3 = new Player(player3, this);
    }

    public long getMatchID() {
        return matchID;
    }


    public void setMatchID(long matchID) {
        this.matchID = matchID;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        if(!simpleGame){
            this.card1 = card1;
        }
        else{
            throw new RuntimeException("Gioco senza carte");
        }
    }

    //TODO: sostituire i setter delle cards con addCard(card:Card):void

    //TODO: unico getter per Card
    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {

        if(!simpleGame){
            this.card1 = card2;
        }
        else{
            throw new RuntimeException("Gioco senza carte");
        }
    }
    public Card getCard3() {
        return card2;
    }

    public void setCard3(Card card3) {
        if(!simpleGame){
            this.card1 = card3;
        }
        else{
            throw new RuntimeException("Gioco senza carte");
        }
    }

    //TODO: unico getter per Player
    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer3() {
        return player3;
    }

    public void setPlayer3(Player player3) {
        this.player3 = player3;
    }

    public boolean isSimple(){return this.simpleGame;}

    public Board getBoard() {
        return board;
    }
}
