package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Server.Session;

import java.util.HashMap;

public class SessionListMessage implements Message {
    private HashMap<String,Integer> partecipants = new HashMap<>();
    private HashMap<String,Boolean> cards = new HashMap<>();

    public SessionListMessage(HashMap<String, Session> availableSession) {
        for(String s: availableSession.keySet()){
            partecipants.put(s,availableSession.get(s).getParticipant());
            cards.put(s,availableSession.get(s).isSimple());
        }
    }

    public HashMap<String, Integer> getPartecipants() {
        return partecipants;
    }

    public HashMap<String, Boolean> getCards() {
        return cards;
    }
}
