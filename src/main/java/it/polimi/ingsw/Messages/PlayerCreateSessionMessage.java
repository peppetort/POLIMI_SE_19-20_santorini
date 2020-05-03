package it.polimi.ingsw.Messages;

public class PlayerCreateSessionMessage implements Message{
    private int players;
    private boolean simple;
    private String username;
    private String session;

    public PlayerCreateSessionMessage(String username, String session,int players, boolean simple) {
        this.players = players;
        this.simple = simple;
        this.username = username;
        this.session = session;
    }

    public int getPlayers() {
        return players;
    }

    public boolean isSimple() {
        return simple;
    }

    public String getUsername() {
        return username;
    }

    public String getSession() {
        return session;
    }


}
