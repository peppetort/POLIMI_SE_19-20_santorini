package it.polimi.ingsw.Messages;

import java.util.HashMap;

public class SessionListMessage implements Message {
    private final HashMap<String,Integer> participants = new HashMap<>();
    private final HashMap<String,Boolean> cards = new HashMap<>();

    public void addSession(String name, int participants, boolean cards){
        this.participants.put(name, participants);
        this.cards.put(name, cards);
    }

    public HashMap<String, Integer> getParticipants() {
        return participants;
    }

    public HashMap<String, Boolean> getCards() {
        return cards;
    }
}
