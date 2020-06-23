package it.polimi.ingsw.Messages;




/**
 * message from client to server used to join an existing {@link it.polimi.ingsw.Server.Session}
 *
 */

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
