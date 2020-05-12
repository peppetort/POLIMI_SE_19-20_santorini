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
	public static Scanner reader = new Scanner(System.in);

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

	private void takeInput() {
		//TODO IL CONTROLLO DELLA CORRETTEZZA DEVE ESSERE FATTO LATO SERVER
		new Thread(() -> {
			boolean valid;
			do {
				valid = true;
				String input;
				String[] data;
				Actions action;
				System.out.println("Insert action:");
				System.out.print("> ");
				try {
					input = reader.nextLine();

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
							System.out.println("Invalid command");
							valid = false;
					}
				} catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e){
					System.out.println("Invalid command");
					valid = false;
				}catch (IndexOutOfBoundsException ignored){};
			}while (!valid);

		}).start();
	}

	public void startMenu() {
		try {
			reader = new Scanner(System.in);
			boolean correct;

			System.out.println("List of commands:");
			System.out.println("JOIN (join an existing session)");
			System.out.println("CREATE (create a new game)");
			System.out.print("\n> ");

			do {
				String input = reader.nextLine();
				if (input.toUpperCase().equals("JOIN") || input.toUpperCase().equals("CREATE")) {
					switch (input.toUpperCase()) {
						case "JOIN":
							System.out.println("List of available session:");
							notify(new PlayerRetrieveSessions());
							break;
						case "CREATE":
							create();
							break;
					}
					correct = true;
				} else {
					System.out.println("Type a valid command");
					System.out.print("> ");
					correct = false;
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

		do {
			switch (question) {
				case 0:
					System.out.println("Insert your name:");
					System.out.print("> ");
					input = reader.nextLine();
					username = input.toUpperCase();
					correct = true;
					break;
				case 1:
					System.out.println("Insert name of the session:");
					System.out.print("> ");
					input = reader.nextLine();
					session = input;
					correct = true;
					break;
				case 2:
					System.out.println("Insert number of players:");
					System.out.print("> ");
					input = reader.nextLine();
					players = Integer.parseInt(input);
					correct = players >= 2 && players <= 3;
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
		}
	}


	public void join(SessionListMessage message) {
		HashMap<String, Integer> participants = message.getParticipants();
		HashMap<String, Boolean> cards = message.getCards();
		String session;
		String username;
		boolean correct = false;

		printer.printAvailableSession(participants, cards);

		if (participants.size() != 0) {


			System.out.println("Insert your username (type esc to go back to startMenu):");
			System.out.print("> ");
			username = reader.nextLine().toUpperCase();

			if (username.toUpperCase().equals("ESC")) {

				startMenu();
			} else {

				do {
					System.out.println("Insert the name of the session you want to join:");
					System.out.print("> ");

					session = reader.nextLine();
					if (participants.containsKey(session)) {
						correct = true;
						notify(new PlayerSelectSession(session, username));
					}
					if (!correct) {
						System.out.println("This session doesn't exists!");
					}
				} while (!correct && !session.toUpperCase().equals("ESC"));

				if (session.toUpperCase().equals("ESC")) {
					startMenu();
				}
			}
		} else {
			System.out.println("No sessions available");
			startMenu();
		}
	}


	@Override
	public void update(Object message) {

		if (message instanceof Integer) {
			if ((Integer) message == 0) {
				this.startMenu();
			} else if ((Integer) message == 1) {
				printer.printBoard();
			} else if ((Integer) message == 2) {

				printer.printStatus();


				if (client.getStatus().myTurn()) {
					takeInput();
				}
			} else if ((Integer) message == 3) {
				printer.printAllCards();
			} else if ((Integer) message == 4) {
				printer.printDeck();
			} else if ((Integer) message == 5) {
				startMenu();
			} else if ((Integer) message == 6) {
				startMenu();
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
		}else if(message instanceof InvalidChoiceMessage){
			System.out.println(((InvalidChoiceMessage) message).getMessage());
			takeInput();
		} else {
			System.out.println(message);
		}
	}
}
















