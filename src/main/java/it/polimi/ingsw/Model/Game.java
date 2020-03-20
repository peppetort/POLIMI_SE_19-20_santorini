package it.polimi.ingsw.Model;

public class Game {
    public static long matchID
    private long matchID;
    private Card card1;
    private Card card2;
    private Card card3;
    private Player player1;
    private Player player2;
    private Player player3;
    private Board board;



    public Game(String player1, String player2,Board board){
        this.board = board;
        this.player1 = new Player(player1, this);
        this.player2 = new Player(player2, this);

    }
    public Game(String player1,String player2,String player3,Board board){
        this.board = board;
        this.player1 = new Player(player1, this);
        this.player2 = new Player(player2, this);
        this.player3 = new Player(player3, this);

    }

    public long getMatchID() {
        return matchID;
    }

    //TODO: settare matchID nel costruttore?
    public void setMatchID(long matchID) {
        this.matchID = matchID;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

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

}
