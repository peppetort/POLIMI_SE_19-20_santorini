package it.polimi.ingsw.Controller;


import it.polimi.ingsw.Exceptions.CardAlreadySetException;
import it.polimi.ingsw.Exceptions.PlayerLostException;
import it.polimi.ingsw.Exceptions.SimpleGameException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Observer.Observer;

import java.util.*;

/**
 * Controller class for MVC pattern implementation.
 */
public class Controller implements Observer<Message> {

	private final Game game;
	private final ArrayList<Player> playersList;
	private final Set<God> cards = new HashSet<>();
	private final HashMap<Player, Boolean> turn = new HashMap<>();

	//timer e task per il calcolo dei 5 secondi
	private final Timer timer = new Timer();
	private TimerTask task;

	/**
	 * Constructor, takes {@link Game} which is the Model itself.
	 * @param game
	 */
	public Controller(Game game) {
		this.game = game;
		playersList = game.getPlayers();
		Player startPlayer;

		for (Player p : game.getPlayers()) {
			turn.put(p, false);
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

	/**
	 * Used to remove a {@link Player} from the turn list and from {@link Game}.
	 * @param player
	 */
	private void removePlayer(Player player) {
		playersList.remove(player);
		turn.remove(player);
		game.removePlayer(player);
	}

	/**
	 * Method used to update the turn. The {@link Game} will notify each {@link it.polimi.ingsw.View.RemoteView} with a
	 * {@link TurnUpdateMessage}.
	 */
	private void updateTurn() {
		int nextPlayerIndex;
		Player nextPlayer;
		HashMap<Actions, Boolean> playerMenu;
		HashMap<Actions, Boolean> nextPlayerMenu;

		game.turnUpdate();

		ActionsUpdateMessage message = new ActionsUpdateMessage();

		for (Player player : playersList) {
			if (turn.get(player)) {
				playerMenu = player.getPlayerMenu();
				nextPlayerIndex = (playersList.indexOf(player) + 1) % (playersList.size());
				nextPlayer = playersList.get(nextPlayerIndex);
				nextPlayerMenu = nextPlayer.getPlayerMenu();

				String nextUsername = nextPlayer.getUsername();
				Color nextColor = nextPlayer.getColor();
				God nextGod;

				try{
					nextGod = nextPlayer.getCard();
				}catch (SimpleGameException e){
					nextGod = null;
				}

				TurnUpdateMessage turnMessage = new TurnUpdateMessage(nextUsername, nextColor, nextGod);
				game.notify(turnMessage);

				//Finito il giro
				if (nextPlayerIndex == 1) {
					if (playerMenu.get(Actions.DECK)) {
						playerMenu.replace(Actions.DECK, false);
						nextPlayerMenu.replace(Actions.CARD, true);

						message.addAction(Actions.CARD);
						//nextPlayer.notify(message);
						game.notify(message);
					} else if (playerMenu.get(Actions.CARD)) {
						playerMenu.replace(Actions.CARD, false);
						nextPlayerMenu.replace(Actions.PLACE, true);
						message.addAction(Actions.PLACE);
						//nextPlayer.notify(message);
						game.notify(message);
					} else if (playerMenu.get(Actions.PLACE)) {
						playerMenu.replace(Actions.PLACE, false);
						nextPlayerMenu.replace(Actions.SELECT, true);

						message.addAction(Actions.SELECT);
						//nextPlayer.notify(message);
						game.notify(message);
					} else {
						nextPlayerMenu.replace(Actions.SELECT, true);

						message.addAction(Actions.SELECT);
						//nextPlayer.notify(message);
						game.notify(message);
					}
				} else {
					if (playerMenu.get(Actions.CARD)) {
						playerMenu.replace(Actions.CARD, false);
						nextPlayerMenu.replace(Actions.CARD, true);

						message.addAction(Actions.CARD);
						//nextPlayer.notify(message);
						game.notify(message);
					} else if (playerMenu.get(Actions.PLACE)) {
						playerMenu.replace(Actions.PLACE, false);
						nextPlayerMenu.replace(Actions.PLACE, true);

						message.addAction(Actions.PLACE);
						//nextPlayer.notify(message);
						game.notify(message);
					} else {
						nextPlayerMenu.replace(Actions.SELECT, true);

						message.addAction(Actions.SELECT);
						//nextPlayer.notify(message);
						game.notify(message);
					}
				}

				turn.replace(player, false);
				turn.replace(nextPlayer, true);
				break;
			}
		}
	}

	public HashMap<Player, Boolean> getTurn() {
		return turn;
	}

	public Set<God> getCards() {
		return cards;
	}

	/**
	 * Method used to perform a selection (the {@link it.polimi.ingsw.Client.Client} select one of his worker and sends
	 * a {@link PlayerSelectMessage}).
	 * @param message
	 */
	private void performStart(PlayerSelectMessage message) {
		Player player = message.getPlayer();
		Worker worker = message.getWorker();

		if (turn.get(player)) {
			if (player.getPlayerMenu().get(Actions.SELECT)) {
				try {
					player.getTurn().start(worker);
				} catch (PlayerLostException e1) {
					LostMessage lostMessage = new LostMessage(player.getUsername(), player.getColor());
					game.notify(lostMessage);
					updateTurn();
					removePlayer(player);

					if (playersList.size() == 1) {
						Player winner = playersList.get(0);
						WinMessage winMessage = new WinMessage(winner.getUsername());
						game.notify(winMessage);
						removePlayer(winner);
					}

				} catch (RuntimeException e2) {
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e2.getMessage());
					player.notify(invalidMessage);
				}
			} else {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't start");
				player.notify(invalidMessage);
			}
		}

	}

	/**
	 * Method used to perform a move if the {@link it.polimi.ingsw.Client.Client} has the turn.
	 * the {@link it.polimi.ingsw.Client.Client} select where to move and sends a {@link PlayerMoveMessage}. If it's an
	 * illegal move the {@link Player} will notify his own {@link it.polimi.ingsw.View.RemoteView} an
	 * {@link InvalidChoiceMessage}. If the {@link Player} moves to a level 3 building the {@link Game} will notify
	 * each {@link it.polimi.ingsw.View.RemoteView} with a {@link WinMessage} which contains the username of the winner.
	 * @param message
	 */
	private void performMove(PlayerMoveMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		int x = message.getX();
		int y = message.getY();

		if (turn.get(player)) {
			if (player.getPlayerMenu().get(Actions.MOVE)) {

				try {
					playerTurn.move(x, y);

					if (playerTurn.won()) {
						WinMessage winMessage = new WinMessage(player.getUsername());
						game.notify(winMessage);

						while (!playersList.isEmpty()) {
							removePlayer(playersList.get(0));
						}
					}
				} catch (RuntimeException e) {
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
					player.notify(invalidMessage);
				}
			} else {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't move");
				player.notify(invalidMessage);
			}
		}
	}

	/**
	 * Method used to perform a build if the {@link it.polimi.ingsw.Client.Client} has the turn.
	 * the {@link it.polimi.ingsw.Client.Client} select where to build and sends a {@link PlayerBuildMessage}. If it's an
	 * illegal move the {@link Player} will notify his own {@link it.polimi.ingsw.View.RemoteView} an {@link InvalidChoiceMessage}.
	 * After a "successful" build a timer will start letting the {@link it.polimi.ingsw.Client.Client} a time window
	 * of 5 seconds to perform an undo and return to the select phase.
	 * @param message
	 */
	private void performBuild(PlayerBuildMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		int x = message.getX();
		int y = message.getY();

		if (turn.get(player)) {
			if (player.getPlayerMenu().get(Actions.BUILD)) {
				try {
					playerTurn.build(x, y);
					task = new TimerTask() {
						@Override
						public void run() {
							performEnd(new PlayerEndMessage(player));
						}
					};
					//solo se il player non può costruire e muovere
					if (!player.getPlayerMenu().get(Actions.BUILD) && !player.getPlayerMenu().get(Actions.MOVE)) {
						timer.schedule(task, 5000);
					}

				} catch (RuntimeException e) {
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
					player.notify(invalidMessage);
				}

			} else {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't build");
				player.notify(invalidMessage);
			}
		}
	}

	/**
	 * Method used by {@link God} Atlas to perform a build-dome if the {@link it.polimi.ingsw.Client.Client} has the turn.
	 * the {@link it.polimi.ingsw.Client.Client} select where to build a dome and sends a {@link PlayerBuildDomeMessage}. If it's an
	 * illegal move the {@link Player} will notify his own {@link it.polimi.ingsw.View.RemoteView} an {@link InvalidChoiceMessage}.
	 * After a "successful" build a timer will start letting the {@link it.polimi.ingsw.Client.Client} a time window
	 * of 5 seconds to perform an undo and return to the select phase.
	 * @param message
	 */
	private void performBuildDome(PlayerBuildDomeMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		int x = message.getX();
		int y = message.getY();

		if (player.getCard().equals(God.ATLAS)) {
			if (turn.get(player)) {
				if (player.getPlayerMenu().get(Actions.BUILD)) {
					try {
						playerTurn.buildDome(x, y);

						task = new TimerTask() {

							@Override
							public void run() {

								performEnd(new PlayerEndMessage(player));
							}
						};
						//solo se il player non può costruire e muovere
						if (!player.getPlayerMenu().get(Actions.BUILD) && !player.getPlayerMenu().get(Actions.MOVE)) {
							timer.schedule(task, 5000);
						}
					} catch (RuntimeException e) {
						InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
						player.notify(invalidMessage);
					}
				} else {
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't build");
					player.notify(invalidMessage);
				}
			}
		} else {
			InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't build a dome");
			player.notify(invalidMessage);
		}

	}

	/**
	 * Method used to pass the turn (finished each phase) if the {@link it.polimi.ingsw.Client.Client} has the turn.
	 * the {@link it.polimi.ingsw.Client.Client} sends a {@link PlayerEndMessage}. If it's an
	 * illegal message the {@link Player} will notify his own {@link it.polimi.ingsw.View.RemoteView} an {@link InvalidChoiceMessage}.
	 * @param message
	 */
	private void performEnd(PlayerEndMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		try {
			task.cancel();
		}catch(NullPointerException ignored){}

		if (turn.get(player)) {
			if (player.getPlayerMenu().get(Actions.END)) {
				try {
					playerTurn.end();
					updateTurn();
				} catch (RuntimeException e) {
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
					player.notify(invalidMessage);
				}
			} else {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't end");
				player.notify(invalidMessage);
			}
		}
	}

	/**
	 * Method used to perform an Undo if the {@link it.polimi.ingsw.Client.Client} has the turn and its in the correct
	 * phase. The {@link it.polimi.ingsw.Client.Client} sends a {@link PlayerEndMessage}. If it cannot be performed
	 * the {@link Player} will notify his own {@link it.polimi.ingsw.View.RemoteView} an {@link InvalidChoiceMessage}.
	 * @param message
	 */
	private void performUndo(PlayerUndoMessage message) {
		Player player = message.getPlayer();
		Turn playerTurn = player.getTurn();
		try {
			task.cancel();
		} catch (Exception ignored) {}

		if (turn.get(player)) {
			if (!player.getPlayerMenu().get(Actions.PLACE) && !player.getPlayerMenu().get(Actions.CARD) && !player.getPlayerMenu().get(Actions.DECK)) {
				try {
					playerTurn.undo();
				} catch (RuntimeException e) {
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
					player.notify(invalidMessage);
				}
			} else {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't undo");
				player.notify(invalidMessage);
			}
		}
	}

	/**
	 * When the {@link it.polimi.ingsw.Client.Client} is "challenger" he has to choose each card and sends a
	 * {@link PlayerDeckMessage} which contains each card name. If an {@link IllegalArgumentException} will be catched
	 * the {@link Player} will notify his {@link it.polimi.ingsw.View.RemoteView} with a {@link InvalidChoiceMessage}.
	 * The number of cards & others exceptions (not your turn or {@link SimpleGameException}) will be catched and
	 * the {@link Player} will notify his {@link it.polimi.ingsw.View.RemoteView} with a {@link InvalidChoiceMessage}.
	 * @param message
	 */
	private void performDeckBuilding(PlayerDeckMessage message) {

		Player player = message.getPlayer();
		Set<God> sCards = message.getDeck();
		ArrayList<God> deck = new ArrayList<>();
		ArrayList<God> gods = new ArrayList<>();

		if (turn.get(player) && player.equals(playersList.get(0))) {
			if (player.getPlayerMenu().get(Actions.DECK)) {
				if (sCards.size() == playersList.size()) {
					try {
						for (God g : sCards) {
							gods.add(g);
							deck.add(g);
							this.cards.add(g);
						}
						game.addCards(deck);
						DeckUpdateMessage deckMessage = new DeckUpdateMessage(gods);
						game.notify(deckMessage);
						updateTurn();
					} catch (SimpleGameException e1) {
						e1.printStackTrace();
					} catch (IllegalArgumentException e2) {
						cards.clear();
						InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e2.getMessage());
						player.notify(invalidMessage);
					}
				} else {
					cards.clear();
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("The number of cards chosen differs from the number of players");
					player.notify(invalidMessage);
				}
			} else {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't build deck");
				player.notify(invalidMessage);
			}
		}
	}

	/**
	 * When a {@link it.polimi.ingsw.Client.Client} select a card sends a {@link PlayerCardChoiceMessage}. If the turn is
	 * correct and the card is in deck list the {@link Player} will have the selected card. If exceptions
	 * {@link NullPointerException}/{@link IllegalArgumentException} / {@link SimpleGameException} / {@link CardAlreadySetException}
	 * the {@link Player} will notify his own {@link it.polimi.ingsw.View.RemoteView}.
	 * @param message
	 */
	private void performCardChoice(PlayerCardChoiceMessage message) {
		Player player = message.getPlayer();
		God cardName = message.getCard();

		if (turn.get(player)) {
			if (player.getPlayerMenu().get(Actions.CARD)) {
				try {
					God card = null;
					for (God c : cards) {
						if (c.equals(cardName)) {
							card = c;
						}
					}
					player.setCard(card);
					cards.remove(card);
					ArrayList<God> deck = new ArrayList<>(cards);
					DeckUpdateMessage deckMessage = new DeckUpdateMessage(deck);
					game.notify(deckMessage);

					updateTurn();
				} catch (NullPointerException | IllegalArgumentException | SimpleGameException | CardAlreadySetException e) {
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
					player.notify(invalidMessage);
				}
			} else {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't choose card");
				player.notify(invalidMessage);
			}
		}
	}

	/**
	 * Method used to perform a Pawn positioning on the {@link Board} of the {@link Game}. The {@link PlayerPlacePawnsMessage}
	 * contains two set of coordinates for the two {@link Worker}. The coordinates have to be correct and the {@link Player}
	 * have to have the turn or the {@link Player} will notify his {@link it.polimi.ingsw.View.RemoteView} with a
	 * {@link InvalidChoiceMessage}.
	 * @param message {@link Message}
	 */
	private void performPawnPositioning(PlayerPlacePawnsMessage message) {
		Player player = message.getPlayer();
		int worker1X = message.getX1();
		int worker1Y = message.getY1();
		int worker2X = message.getX2();
		int worker2Y = message.getY2();
		Board board = game.getBoard();

		Worker worker1 = player.getWorker1();
		Worker worker2 = player.getWorker2();

		if (turn.get(player)) {
			if (player.getPlayerMenu().get(Actions.PLACE)) {
				try {
					if (!board.getBox(worker1X, worker1Y).isFree() || !board.getBox(worker2X, worker2Y).isFree()) {
						InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("Can't place pawns here! The positions chosen are already occupied");
						player.notify(invalidMessage);
					} else {
						try {
							board.initializePawn(worker1, worker2, worker1X, worker1Y, worker2X, worker2Y);
							updateTurn();
						}catch (RuntimeException e){
							InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
							player.notify(invalidMessage);
						}
					}
				} catch (IndexOutOfBoundsException e) {
					InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage(e.getMessage());
					player.notify(invalidMessage);
				}
			} else {
				InvalidChoiceMessage invalidMessage = new InvalidChoiceMessage("You can't place your pawns");
				player.notify(invalidMessage);
			}
		}
	}


	/**
	 * Method used to remove a {@link Player} from the {@link Game}. The turn will be updated. If there are only one
	 * {@link Player} remaining it will be notified with a {@link WinMessage}.
	 * @param message
	 */
	private void performPlayerRemoval(PlayerRemoveMessage message) {
		String playerName = message.getPlayer();
		Player player = null;

		for (Player p : playersList) {
			if (p.getUsername().equals(playerName)) {
				player = p;
			}
		}

		if (player != null) {
			if (turn.get(player)) {
				if (player.getPlayerMenu().get(Actions.DECK)) {
					removePlayer(player);
					Player firstPlayer = playersList.get(0);
					firstPlayer.getPlayerMenu().replace(Actions.DECK, true);
					turn.replace(firstPlayer, true);
				} else {
					if(playersList.size() == 3) {
						updateTurn();
					}
				}
			}

			if (playersList.size() == 3) {
				removePlayer(player);
			} else {
				removePlayer(player);
				WinMessage winMessage = new WinMessage(playersList.get(0).getUsername());
				game.notify(winMessage);
				removePlayer(playersList.get(0));
			}
		}
	}

	/**
	 * The {@link Game} will notify each {@link it.polimi.ingsw.View.RemoteView} with a {@link ChatUpdateMessage}.
	 * @param message
	 */
	private void performChatUpdate(PlayerChatMessage message){
		game.updateChat(message.getPlayer(),message.getMessage());
	}

	/**
	 * The {@link it.polimi.ingsw.Server.SocketClientConnection} notifies his {@link Controller} with the {@link Message}
	 * read on his ObjectInpuStream.
	 * @param message
	 */
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
		if (message instanceof PlayerChatMessage) {
			performChatUpdate((PlayerChatMessage) message);
		}
	}
}
