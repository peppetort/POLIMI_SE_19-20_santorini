package it.polimi.ingsw.GUI;

/**
 * A {@link SessionObject} is used in the {@link JoinMenuController} to represent each {@link it.polimi.ingsw.Server.Session}
 * in the session's {@link javafx.scene.control.TableView}.
 */
public class SessionObject {

    String name;
    int players;
    boolean cards;

    /**
     * Constructor.
     * @param name
     * @param players
     * @param cards
     */
    public SessionObject(String name, int players, boolean cards) {
        this.name = name;
        this.players = players;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
