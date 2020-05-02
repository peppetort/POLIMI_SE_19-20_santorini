package it.polimi.ingsw.Messages;

public class PlayerCreateSessionMessage implements Message{
    private int players;
    private boolean cards;
    private String username;
    private String session;

    public PlayerCreateSessionMessage(String username, String session,int players, boolean cards) {
        this.players = players;
        this.cards = cards;
        this.username = username;
        this.session = session;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public boolean isCards() {
        return cards;
    }

    public void setCards(boolean cards) {
        this.cards = cards;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
