package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Exceptions.CardAlreadySetException;
import it.polimi.ingsw.Exceptions.PlayerLostException;
import it.polimi.ingsw.Exceptions.SimpleGameException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.*;


public class Controller extends Observable<Message> implements Observer<Message> {

	private final Game game;
	private final ArrayList<Player> playersList;
	private final Set<God> cards = new HashSet<>();
	private final HashMap<Player, Boolean> turn = new HashMap<>();
	private final HashMap<Player, Boolean> outcome = new HashMap<>();
	private Player playerUndo;
	private int loosingPlayers;

	//timer e task per il calcolo dei 5 secondi
	private final Timer timer = new Timer();
	private TimerTask task;

	public Controller(Game game) {
		this.game = game;
		playersList = game.getPlayers();
		loosingPlayers = 0;
		Player startPlayer;

		for (Player p : game.getPlayers()) {
			turn.put(p, false);
			outcome.put(p, null);
		}

		if (game.isSimple()) {
			startPlayer = playersList.get(1);
			startPlayer.getPlayerMenu().replace(Actions.PLACE, true);
			turn.replace(game.getPlayers().get(1), true);
		} else {
			startPlayer = playersList.get(0);
			startPlayer.getPlayerMenu().replace(Actions.DECK, true);
			turn.replace(game.getPlayers().get(0), true);
		}

	}

	//todo: fare la deregister in caso di vittoria o sconfitta

	private void updateTurn() {
		int nextPlayerIndex;
		Player nextPlayer;
		HashMap<Actions, Boolean> playerMenu;
		HashMap<Actions, Boolean> nextPlayerMenu;

		//Unico giocatore rimasto vince
		if (outcome.size() == 1) {
			outcome.replaceAll((key, value) -> true);

			Player winner = playersList.get(0);

			WinMessage winMessage = new WinMessage(winner.getUsername());
			notify(winMessage);
		} else {

			ActionsUpdateMessage message = new ActionsUpdateMessage();

			for (Player player : playersList) {
				if (turn.get(player)) {
					playerMenu = player.getPlayerMenu();
					nextPlayerIndex = (playersList.indexOf(player) + 1) % (playersList.size());
					nextPlayer = playersList.get(nextPlayerIndex);
					nextPlayerMenu = nextPlayer.getPlayerMenu();

					//Altri giocatori hanno perso -> il rimanente vince
					if (playersList.size() == loosingPlayers + 1 && outcome.get(nextPlayer) == null) {
						outcome.replace(nextPlayer, true);

						WinMessage winMessage = new WinMessage(nextPlayer.getUsername());
						notify(winMessage);

					} else {

						TurnUpdateMessage turnMessage = new TurnUpdateMessage(nextPlayer.getUsername());
						notify(turnMessage);

						//Finito il giro
						if (nextPlayerIndex == 1) {
							if (playerMenu.get(Actions.DECK)) {
								playerMenu.replace(Actions.DECK, false);
								nextPlayerMenu.replace(Actions.CARD, true);

								message.addAction(Actions.CARD);
								nextPlayer.notify(message);
							} else if (playerMenu.get(Actions.CARD)) {
								playerMenu.replace(Actions.CARD, false);
								nextPlayerMenu.replace(Actions.PLACE, true);

								message.addAction(Actions.PLACE);
								nextPlayer.notify(message);
							} else if (playerMenu.get(Actions.PLACE)) {
								playerMenu.replace(Actions.PLACE, false);
								nextPlayerMenu.replace(Actions.SELECT, true);

								message.addAction(Actions.SELECT);
								nextPlayer.notify(message);
							} else {
								nextPlayerMenu.replace(Actions.SELECT, true);

								message.addAction(Actions.SELECT);
								nextPlayer.notify(message);
							}
						} else {
							if (playerMenu.get(Actions.CARD)) {
								playerMenu.replace(Actions.CARD, false);
								nextPlayerMenu.replace(Actions.CARD, true);

								message.addAction(Actions.CARD);
								nextPlayer.notify(message);
							} else if (playerMenu.get(Actions.PLACE)) {
								playerMenu.replace(Actions.PLACE, false);
								nextPlayerMenu.replace(Actions.PLACE, true);

								message.addAction(Actions.PLACE);
								nextPlayer.notify(message);
							} else {
								nextPlayerMenu.replace(Actions.SELECT, true);

								message.addAction(Actions.SELECT);
								nextPlayer.notify(message);
							}
						}

						turn.replace(player, false);
						turn.replace(nextPlayer, true);
						break;
					}
				}
			}
		}
	}

	public HashMap<Player, Boolean> getTurn() {
		return turn;
	}

	public HashMap<Player, Boolean> getOutcome() {
		return outcome;
	}

	public Set<God> getCards() {
		return cards;
	}

	//Actions
	//todo: deregister quando invio un lost message
	private void performStart(PlayerSelectMessage message) {
		Player player = message.getPlayer();
		Worker worker = message.getWorker();

		if (turn.get(player) && player.getPlayerMenu().get(Actions.SELECT) && outcome.get(player) == null) {

			if (outcome.get(player) == null && loosingPlayers == playersList.size() - 1) {
				outcome.replace(player, true);
			}

			try {
				player.getTurn().start(worker);
				//canUndo=true;
			} catch (PlayerLostException e1) {
				outcome.replace(player, false);
				loosingPlayers++;

				LostMessage lostMessage = new LostMessage(player.getUsername(), player.getColor());
				notify(lostMessage);
				updateTurn();
			} catch (RuntimeException e2) {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e2.getMessage());
				player.notify(invalidMessage);
			}
		}

	}

	private void performMove(PlayerMoveMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		int x = message.getX();
		int y = message.getY();

		try {
			if (turn.get(player) && player.getPlayerMenu().get(Actions.MOVE) && outcome.get(player) == null) {
				playerTurn.move(x, y);

				if (playerTurn.won()) {
					outcome.replaceAll((key, value) -> false);
					outcome.replace(player, true);
					loosingPlayers = playersList.size() - 1;

					WinMessage winMessage = new WinMessage(player.getUsername());

					for (Player p : playersList) {
						p.notify(winMessage);
					}
				}
			}
		} catch (RuntimeException e) {
			InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
			player.notify(invalidMessage);
		}
	}

	private void performBuild(PlayerBuildMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		int x = message.getX();
		int y = message.getY();

		try {
			if (turn.get(player) && player.getPlayerMenu().get(Actions.BUILD) && outcome.get(player) == null) {
				playerTurn.build(x, y);
				// canUndo=false;
				playerUndo = player;
				task = new TimerTask() {

					@Override
					public void run() {

						performEnd(new PlayerEndMessage(playerUndo));
					}
				};
				//solo se il player non può costruire e muovere
				if (!player.getPlayerMenu().get(Actions.BUILD) && !player.getPlayerMenu().get(Actions.MOVE)) {
					timer.schedule(task, 5000);
				}

			}
		} catch (RuntimeException e) {
			InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
			player.notify(invalidMessage);
		}
	}

	private void performBuildDome(PlayerBuildDomeMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		int x = message.getX();
		int y = message.getY();

		try {
			if (player.getCard().equals(God.ATLAS)) {
				if (turn.get(player) && player.getPlayerMenu().get(Actions.BUILD) && outcome.get(player) == null) {
					playerTurn.buildDome(x, y);

					playerUndo = player;
					task = new TimerTask() {

						@Override
						public void run() {

							performEnd(new PlayerEndMessage(playerUndo));
						}
					};
					//solo se il player non può costruire e muovere
					if (!player.getPlayerMenu().get(Actions.BUILD) && !player.getPlayerMenu().get(Actions.MOVE)) {
						timer.schedule(task, 5000);
					}
				}
			}
		} catch (RuntimeException e) {
			InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
			player.notify(invalidMessage);
		}
	}


	private void performEnd(PlayerEndMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();

		try {
			if (turn.get(player) && player.getPlayerMenu().get(Actions.END) && outcome.get(player) == null) {
				playerTurn.end();
				updateTurn();
			}
		} catch (RuntimeException e) {
			InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
			player.notify(invalidMessage);
		}
	}

	private void performUndo(PlayerUndoMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		try {
			task.cancel();
		} catch (Exception ignored) {
		}

		try {
			if (turn.get(player) && outcome.get(player) == null && !player.getPlayerMenu().get(Actions.PLACE) && !player.getPlayerMenu().get(Actions.CARD) && !player.getPlayerMenu().get(Actions.DECK)) {
				playerTurn.undo();
			}
		} catch (RuntimeException e) {
			InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
			player.notify(invalidMessage);
		}

		//else{throw new RuntimeException("Invalid command");}

	}

	private void performDeckBuilding(PlayerDeckMessage message) {
		//todo: sistemare le strutture dati e metodi

		Player player = message.getPlayer();
		Set<God> sCards = message.getDeck();
		ArrayList<God> deck = new ArrayList<>();
		ArrayList<God> gods = new ArrayList<>();

		if (turn.get(player) && player.getPlayerMenu().get(Actions.DECK) && player.equals(playersList.get(0))) {
			//TODO: CONTROLLO DIMENSIONE DECK lancio eccezione
			if (sCards.size() == playersList.size()) {
				try {
					for (God g : sCards) {

						gods.add(g);
						deck.add(g);
						this.cards.add(g);
					}
					game.addCards(deck);
					DeckUpdateMessage deckMessage = new DeckUpdateMessage(gods);
					notify(deckMessage);
					updateTurn();
				} catch (SimpleGameException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e2) {
					cards.clear();
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e2.getMessage());
					player.notify(invalidMessage);
				}
			}
		}
	}

	private void performCardChoice(PlayerCardChoiceMessage message) {
		Player player = message.getPlayer();
		God cardName = message.getCard();

		ArrayList<God> deck = new ArrayList<>();

		try {
			if (player.getPlayerMenu().get(Actions.CARD) && turn.get(player)) {
				try {
					God card = null;
					for (God c : cards) {
						if (c.equals(cardName)) {
							card = c;
						}
					}
					player.setCard(card);
					cards.remove(card);

					for (God c : cards) {
						deck.add(c);
					}
					DeckUpdateMessage deckMessage = new DeckUpdateMessage(deck);
					notify(deckMessage);

					updateTurn();
				} catch (SimpleGameException | CardAlreadySetException e1) {
					e1.printStackTrace();
				}
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
			player.notify(invalidMessage);
		}
	}

	private void performPawnPositioning(PlayerPlacePawnsMessage message) {
		Player player = message.getPlayer();
		int worker1X = message.getX1();
		int worker1Y = message.getY1();
		int worker2X = message.getX2();
		int worker2Y = message.getY2();
		Board board = game.getBoard();

		Worker worker1 = player.getWorker1();
		Worker worker2 = player.getWorker2();

		try {
			if (turn.get(player) && player.getPlayerMenu().get(Actions.PLACE)) {
				if (!board.getBox(worker1X, worker1Y).isFree() || !board.getBox(worker2X, worker2Y).isFree()) {
					throw new RuntimeException("Can't place pawns here! The positions chosen are already occupied");
				}
				board.initializePawn(worker1, worker2, worker1X, worker1Y, worker2X, worker2Y);
				updateTurn();
			}
		} catch (IndexOutOfBoundsException e) {
			InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
			player.notify(invalidMessage);
		}
	}


	private void performPlayerRemoval(PlayerRemoveMessage message) {
		String playerName = message.getPlayer();
		Player player = null;

		for (Player p : playersList) {
			if (p.getUsername().equals(playerName)) {
				player = p;
			}
		}

		if (player != null) {
			outcome.remove(player);
			if (turn.get(player)) {
				if (player.getPlayerMenu().get(Actions.DECK)) {
					playersList.remove(player);
					turn.remove(player);
					Player firstPlayer = playersList.get(0);
					firstPlayer.getPlayerMenu().replace(Actions.DECK, true);
					turn.replace(firstPlayer, true);
				} else {
					updateTurn();
					playersList.remove(player);
					turn.remove(player);
				}
			}
			game.removePlayer(player);
		}

	}


	@Override
	public void update(Message message) {

		if (message instanceof PlayerMoveMessage) {
			performMove((PlayerMoveMessage) message);
		}
		if (message instanceof PlayerBuildMessage) {
			performBuild((PlayerBuildMessage) message);
		}
		if (message instanceof PlayerCardChoiceMessage) {
			performCardChoice((PlayerCardChoiceMessage) message);
		}
		if (message instanceof PlayerDeckMessage) {
			performDeckBuilding((PlayerDeckMessage) message);
		}
		if (message instanceof PlayerSelectMessage) {
			performStart((PlayerSelectMessage) message);
		}
		if (message instanceof PlayerEndMessage) {
			performEnd((PlayerEndMessage) message);
		}
		if (message instanceof PlayerRemoveMessage) {
			performPlayerRemoval((PlayerRemoveMessage) message);
		}
		if (message instanceof PlayerPlacePawnsMessage) {
			performPawnPositioning((PlayerPlacePawnsMessage) message);
		}
		if (message instanceof PlayerBuildDomeMessage) {
			performBuildDome((PlayerBuildDomeMessage) message);
		}
		if (message instanceof PlayerUndoMessage) {
			performUndo((PlayerUndoMessage) message);
		}


	}
}
