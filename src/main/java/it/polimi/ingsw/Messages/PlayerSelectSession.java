package it.polimi.ingsw.Messages;

public class PlayerSelectSession implements Message {
    private String sessionID;
    private String username;

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
