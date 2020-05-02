package it.polimi.ingsw.Messages;

public class PlayerSelectSession implements Message {
    private String sessionID;

    public PlayerSelectSession(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionID() {
        return sessionID;
    }
}
