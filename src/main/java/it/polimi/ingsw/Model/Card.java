package it.polimi.ingsw.Model;

public class Card {
    private God name;

    public Card(God god){
        name = god;
    }
    public God getName() {
        return name;
    }

    public void setName(God name) {
        this.name = name;
    }
}
