package it.polimi.ingsw.CLI;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Client.ClientBoard;
import it.polimi.ingsw.Client.ClientStatus;
import it.polimi.ingsw.Exceptions.AlreadyExistingSessionException;
import it.polimi.ingsw.Exceptions.InvalidPlayersNumberException;
import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Exceptions.SessionNotExistsException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Actions;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI extends Observable<Object> implements Observer {
	private final Printer printer;
	private final Client client;
	private Scanner reader = new Scanner(System.in);
	private volatile boolean threadStop = false;
	private Thread inputThread;

	public CLI(Client client) {
		printer = new Printer();
		this.client = client;
	}

	public void setClientBoard(ClientBoard clientBoard) {
		printer.setClientBoard(clientBoard);
	}

	public void setClientStatus(ClientStatus clientStatus) {
		printer.setClientStatus(clientStatus);
	}

	private boolean isEmptyInput(String input) {
		return input.replaceAll("\\s+", "").equals("");
	}

	private Thread takeInput() {
		return new Thread(() -> {
				boolean valid;
				do {
					valid = true;
					String input;
					String[] data;
					Actions action;
					System.out.println("Insert action || type help for the list of commands:");
					System.out.print("> ");
					try {
						input = reader.nextLine();
						if(!isEmptyInput(input)) {
							if (input.toUpperCase().equals("HELP")) {
								System.out.print("\n");
								System.out.println("> DECK GOD1 GOD2 [GOD33] to choose the available cards for the players (GOD3 only if this is a 3 players match).");
								System.out.println("> CARD GOD to choose your card. (Choose from the available cards selected by player one).");
								System.out.println("> PLACE x1 y1 x2 y2 to place your two pawns: pawn 1 will be placed in column number x1 and row y1 (same for the second pawn).");
								System.out.println("> SELECT 1||2 to select the pawn that will act: you can only choose one pawn.");
								System.out.println("> MOVE x y to move the selected pawn in the spot x (column) - y (row). It has to be a legal move or you will have to redo.");
								System.out.println("> BUILD x y to build in the spot x (column) - y (row). It has to be a legal move or you will have to redo.");
								System.out.println("> END to pass the turn.");
								System.out.println("> UNDO to redo your turn: you will be thrown to select your pawn. If you have built something you have to UNDO your move before the 5-seconds timer ends or you will" +
										"pass the turn automatically.");
								System.out.print("\n");
								input = reader.nextLine();
								System.out.print("> ");
							}
							data = input.split(" ");
							action = Actions.valueOf(data[0].toUpperCase());
							switch (action) {
								case UNDO:
									notify(new PlayerUndoMessage());
									break;
								case BUILD:
									notify(new PlayerBuildMessage(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
									break;
								case END:
									notify(new PlayerEndMessage());
									break;
								case MOVE:
									notify(new PlayerMoveMessage(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
									break;
								case PLACE:
									notify(new PlayerPlacePawnsMessage(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])));
									break;
								case SELECT:
									notify(new PlayerSelectMessage(Integer.parseInt(data[1])));
									break;
								case CARD:
									notify(new PlayerCardChoiceMessage(God.valueOf(data[1].toUpperCase())));
									break;
								case DECK:
									ArrayList<God> deck = new ArrayList<>();
									for (String s : data) {
										if (!s.equals(data[0])) {
											deck.add(God.valueOf(s.toUpperCase()));
										}
									}
									notify(new PlayerDeckMessage(deck));
									break;
								case BUILD_DOME:
									notify(new PlayerBuildDomeMessage(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
									break;
								default:
									valid = false;
							}
						}
					} catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
						System.out.println("Invalid command");
						valid = false;
					} catch (IndexOutOfBoundsException ignored) {
					}
				} while (!threadStop && !valid);
		});
	}

	public void startMenu() {
		try {
			reader = new Scanner(System.in);
			boolean correct;

			printer.printTitle("List of commands");
			System.out.println("JOIN (join an existing session)");
			System.out.println("CREATE (create a new game)\n");

			do {
				System.out.print("> ");
				correct = false;
				String input = reader.nextLine();

				if (!isEmptyInput(input)) {
					if (input.toUpperCase().equals("JOIN") || input.toUpperCase().equals("CREATE")) {
						switch (input.toUpperCase()) {
							case "JOIN":
								notify(new PlayerRetrieveSessions());
								correct = true;
								break;
							case "CREATE":
								create();
								correct = true;
								break;
							default:
								correct = false;
						}
					} else {
						correct = false;
					}
				}
			} while (!correct);

		} catch (InvalidUsernameException e) {
			System.out.println(e.getMessage());
			notify(new PlayerRetrieveSessions());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void create() {

		String input;
		int question = 0;
		String username = null;
		String session = null;
		int players = 2;
		boolean correct;
		boolean simple = false;

		printer.printTitle("CREATE");

		do {
			switch (question) {
				case 0:
					System.out.println("Insert your name:");
					System.out.print("> ");
					input = reader.nextLine();
					if (isEmptyInput(input)) {
						correct = false;
					} else {
						username = input;
						correct = true;
					}
					break;
				case 1:
					System.out.println("Insert name of the session:");
					System.out.print("> ");
					input = reader.nextLine();
					if (isEmptyInput(input)) {
						correct = false;
					} else {
						session = input;
						correct = true;
					}
					break;
				case 2:
					System.out.println("Insert number of players:");
					System.out.print("> ");
					input = reader.nextLine();
					if (isEmptyInput(input)) {
						correct = false;
					} else {
						try {
							players = Integer.parseInt(input);
							correct = players >= 2 && players <= 3;
						} catch (NumberFormatException e) {
							correct = false;
						}
					}
					break;
				case 3:
					System.out.println("Do you want to use cards? Y/N");
					System.out.print("> ");
					input = reader.nextLine();
					if (input.toUpperCase().equals("Y")) {
						simple = false;
						correct = true;
					} else if (input.toUpperCase().equals("N")) {
						simple = true;
						correct = true;
					} else {
						correct = false;
					}
					break;
				default:
					throw new IllegalStateException();
			}
			if (correct) {
				question++;
			}
		} while (!input.toUpperCase().equals("ESC") && question < 4);

		if (!input.toUpperCase().equals("ESC")) {
			PlayerCreateSessionMessage createMessage = new PlayerCreateSessionMessage(username, session, players, simple);
			notify(createMessage);
		} else {
			startMenu();
		}
	}


	public void join(SessionListMessage message) {
		HashMap<String, Integer> participants = message.getParticipants();
		HashMap<String, Boolean> cards = message.getCards();
		String session;
		String username = null;
		boolean correct;

		if (participants.size() != 0) {
			printer.printTitle("Available sessions");
			printer.printAvailableSession(participants, cards);
			printer.printTitle("JOIN");

			System.out.println("Insert your username (type esc to go back to startMenu):");

			do {
				System.out.print("> ");
				String input = reader.nextLine();

				if (!isEmptyInput(input)) {
					username = input;
					correct = true;
				} else {
					correct = false;
				}
			} while (!correct);


			if (username.toUpperCase().equals("ESC")) {
				startMenu();
			} else {

				System.out.println("Insert the name of the session you want to join:");
				String input;
				do {
					System.out.print("> ");
					correct = false;
					input = reader.nextLine();

					if (!isEmptyInput(input)) {
						session = input;
						if (participants.containsKey(session)) {
							correct = true;
							notify(new PlayerSelectSession(session, username));
						}
						if (!correct) {
							System.out.println("This session doesn't exists!");
						}
					}
				} while (!correct && !input.toUpperCase().equals("ESC"));

				if (input.toUpperCase().equals("ESC")) {
					startMenu();
				}
			}
		} else {
			printer.printTitle("No available sessions");
			startMenu();
		}
	}


	@Override
	public void update(Object message){

		if (message instanceof Integer) {
			if ((Integer) message == 0) {
				if(inputThread != null && !threadStop){
					System.out.println("Type ENTER to return to the main menu");
					if(inputThread.isAlive()) {
						threadStop = true;
						try {
							inputThread.join();
						} catch (InterruptedException e) {
							System.err.println(e.getMessage());
						}
						threadStop = false;
					}else {
						reader.nextLine();
					}
				}
				this.startMenu();
			} else if ((Integer) message == 1) {
				printer.printBoard();
			} else if ((Integer) message == 2) {
				printer.printStatus();
				if (client.getStatus().myTurn()) {
					inputThread = takeInput();
					inputThread.start();
				}
			} else if ((Integer) message == 3) {
				printer.printAllCards();
			} else if ((Integer) message == 4) {
				printer.printDeck();
			}
		} else if (message instanceof SessionListMessage) {
			join((SessionListMessage) message);
		} else if (message instanceof InvalidUsernameException) {
			System.out.println(((InvalidUsernameException) message).getMessage());
			notify(new PlayerRetrieveSessions());
		} else if (message instanceof AlreadyExistingSessionException) {
			System.out.println(((AlreadyExistingSessionException) message).getMessage());
			notify(new PlayerRetrieveSessions());
		} else if (message instanceof SessionNotExistsException) {
			System.out.println(((SessionNotExistsException) message).getMessage());
			notify(new PlayerRetrieveSessions());
		} else if (message instanceof InvalidPlayersNumberException) {
			System.out.println(((InvalidPlayersNumberException) message).getMessage());
			create();
		} else if (message instanceof InvalidChoiceMessage) {
			System.out.println(((InvalidChoiceMessage) message).getMessage());
			inputThread = takeInput();
			inputThread.start();
		} else {
			System.out.println(message);
		}
	}
}
















