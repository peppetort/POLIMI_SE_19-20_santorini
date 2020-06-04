package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Actions;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observable;

import java.util.ArrayList;

public class ClientStatus extends Observable {
	private final String username;
	private String card;
	private final Color color;
	private String turnName;
	private Color turnColor;
	private God turnGod;
	private ArrayList<Actions> actions = new ArrayList<>();;
	private final ArrayList<String> messages = new ArrayList<>();
	private ArrayList<God> deck;
	private final int playersNumber;
	private int selected;


	public ClientStatus(String username, Color color, int playersNumber) {
		this.username = username;
		this.color = color;
		this.playersNumber = playersNumber;
	}

	public int getPlayersNumber(){
		return playersNumber;
	}

	public String getCard() {
		return card;
	}

	public Color getColor() {
		return color;
	}

	public Color getTurnColor(){return turnColor;}

	public God getTurnGod(){
		return this.turnGod;
	}

	public ArrayList<Actions> getActions() {
		return actions;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public ArrayList<God> getDeck() {
		return deck;
	}


	public String getTurn() {
		return this.turnName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setCard(God card) {
		if (card != null) {
			this.card = card.toString();
			deck = null;
		}
	}

	public void setSelected(int worker){
		selected = worker;
	}

	public int getSelected(){
		return selected;
	}

	public void updateDeck(ArrayList<God> deck) {
		this.deck = deck;
	}

	public synchronized void updateTurn(String player, Color color, God god) {
		this.turnName = player;
		this.turnColor = color;

		if(god != null){
			this.turnGod = god;
		}

		if (!turnName.equals(username)) {
			messages.add("Wait your turn");
			actions.clear();
			messages.clear();
		}
		notify(2);
	}

	public synchronized void setWinner(String username) {
		this.turnName = null;
		this.actions = null;

		if (username.equals(this.username)) {
			messages.add("YOU WIN :)");
		} else {
			messages.add("YOU LOSE :(");
		}
		notify(2);
		messages.clear();
		notify(0);
		// print();
	}

	public synchronized void lose(String username) {
		if (username.equals(this.username)) {
			this.actions = null;
			this.turnName = null;

			messages.add("YOU LOSE :(");
			//print();
			notify(2);
			messages.clear();
			notify(0);
		} else {
		    messages.add(username + " lost");
			notify(1);
		}

	}

	public synchronized void updateAction(ArrayList<Actions> actions) {
			if(turnName.equals(username)) {
				this.actions = actions;
			}
			if (actions.get(0).equals(Actions.DECK)) {
				notify(3);
			} else if (actions.get(0).equals(Actions.CARD)) {
				notify(4);
			} else if (actions.get(0).equals(Actions.PLACE)) {
				notify(1);
			} else if (!actions.get(0).equals(Actions.SELECT)) {
				messages.clear();
			}
		notify(2);
	}


	public boolean myTurn() {
		try {
			return turnName.equals(username);
		} catch (NullPointerException e) {
			return false;
		}
	}

}
