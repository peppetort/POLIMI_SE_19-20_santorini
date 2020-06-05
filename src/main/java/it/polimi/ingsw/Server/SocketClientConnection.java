package it.polimi.ingsw.Server;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class SocketClientConnection extends Observable<Message> implements ClientConnection, Runnable {

	private final Socket socket;

	private final ObjectOutputStream out;
	private final ObjectInputStream in;

	private final Server server;
	private Session session;
	private String username;

	private boolean active = true;

	public SocketClientConnection(Socket socket, Server server) throws IOException {
		this.socket = socket;
		this.server = server;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	private synchronized boolean isActive() {
		return active;
	}

	@Override
	public synchronized void closeConnection() {
		send("Connection closed!");
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("Error when closing socket!");
		}
		active = false;
		session.deregisterConnection(username);
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public synchronized void send(final Object message) {
		try {
			out.reset();
			out.writeObject(message);
			out.flush();
		} catch (IOException ignored) {}
	}


	@Override
	public void run() {
		Object inputObject;
		try {
			while (isActive()) {
				inputObject = in.readObject();

				if (inputObject instanceof PlayerRetrieveSessions) {

					HashMap<String, Session> availableSessions = server.getAvailableSessions();
					SessionListMessage sessionListMessage = new SessionListMessage();

					for (String session : availableSessions.keySet()) {
						sessionListMessage.addSession(session, availableSessions.get(session).getParticipant(), !availableSessions.get(session).isSimple());
					}
					send(sessionListMessage);

				} else if (inputObject instanceof PlayerCreateSessionMessage) {

					String username = ((PlayerCreateSessionMessage) inputObject).getUsername();
					String sessionID = ((PlayerCreateSessionMessage) inputObject).getSession();
					int players = ((PlayerCreateSessionMessage) inputObject).getPlayers();
					boolean cards = ((PlayerCreateSessionMessage) inputObject).isSimple();

					if (server.getAvailableSessions().get(sessionID) != null) {
						send(new AlreadyExistingSessionException("A session with this name already exists"));
					} else if (players != 2 && players != 3) {
						send(new InvalidPlayersNumberException("It can be played in 2 or 3"));
					} else {
						//TODO DISCUTERE
						send(new SuccessfulCreate());
						this.username = username;
						session = new Session(this, players, cards, server, sessionID);
						server.availableSessions.put(sessionID, session);
					}

				} else if (inputObject instanceof PlayerSelectSession) {

					try{

					this.username = ((PlayerSelectSession) inputObject).getUsername();
					String sessionID = ((PlayerSelectSession) inputObject).getSessionID();

						if (server.availableSessions.get(sessionID).getWaitingConnection().containsKey(username)) {
							send(new InvalidUsernameException("This username is already in use, please insert another username."));
						} else if(!server.availableSessions.containsKey(sessionID)){
							send(new SessionNotExistsException("No session found"));
						}else {
								Session selectedSession = server.availableSessions.get(sessionID);
								if (selectedSession.getWaitingConnection().containsKey(username)) {
									send(new InvalidUsernameException("The chosen name already exists in the selected session"));
								} else {
									try {
										this.session = selectedSession;
										server.availableSessions.get(sessionID).addParticipant(this);
										send(new SuccessfulJoin());
									}catch(FullSessionException e){
										throw new FullSessionException(e.getMessage());
									}
								}
						}
					} catch (NullPointerException e) {
						send(new SessionNotExistsException("No session found"));
					}
				} else if (inputObject instanceof Message) {
						notify((Message) inputObject);
				}

			}
		}catch (InterruptedException | ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException ignored){

		} finally {
			closeConnection();
		}
	}
}

