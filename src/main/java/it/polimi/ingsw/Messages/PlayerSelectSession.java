package it.polimi.ingsw.Messages;

public class PlayerSelectSession implements Message {
    private final String sessionID;
    private final String username;

    public PlayerSelectSession(String sessionID,String username) {
        this.sessionID = sessionID;
        this.username = username;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getUsername(){
        return username;
    }
}
