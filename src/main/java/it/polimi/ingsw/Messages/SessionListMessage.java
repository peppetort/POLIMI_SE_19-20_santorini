package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Server.Session;

import java.util.HashMap;

public class SessionListMessage implements Message {
    private HashMap<String, Session> disponibleSession = new HashMap<>();

    public SessionListMessage(HashMap<String, Session> disponibleSession) {
        this.disponibleSession = disponibleSession;
    }

    public HashMap<String, Session> getDisponibleSession() {
        return disponibleSession;
    }

    public void setDisponibleSession(HashMap<String, Session> disponibleSession) {
        this.disponibleSession = disponibleSession;
    }
}
