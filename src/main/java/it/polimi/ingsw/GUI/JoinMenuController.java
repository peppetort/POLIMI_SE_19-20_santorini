package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Exceptions.SessionNotExistsException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PlayerRetrieveSessions;
import it.polimi.ingsw.Messages.PlayerSelectSession;
import it.polimi.ingsw.Messages.SessionListMessage;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Controller for the JoinMenu.fxml stage. The {@link it.polimi.ingsw.Client.Client} in this stage has the chance to
 * choose a {@link it.polimi.ingsw.Server.Session} to join.
 */
public class JoinMenuController implements Initializable {

	private static MainController mainController;

	private static String session;

	public TableView<SessionObject> sessionsTable;
	public TableColumn<SessionObject, String> name;
	public TableColumn<SessionObject, Integer> players;
	public TableColumn<SessionObject, Boolean> cards;
	public ImageView hourGlass1;
	public ImageView hourGlass2;
	public Button backButton;
	public Button joinButton;

	private static ScheduledExecutorService executor;

	public static ObservableList<SessionObject> list = FXCollections.observableArrayList();

	public static void setMainController(MainController mc) {
		mainController = mc;
	}

	/**
	 * {@link MainController} notifies a {@link PlayerRetrieveSessions} and an {@link java.util.concurrent.ExecutorService}
	 * will be used to updates every seconds the {@link it.polimi.ingsw.Server.Session} list so a {@link it.polimi.ingsw.Model.Player}
	 * cannpt join an already started {@link it.polimi.ingsw.Model.Game}.
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Message msg = new PlayerRetrieveSessions();
		mainController.notify(msg);

		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		players.setCellValueFactory(new PropertyValueFactory<>("players"));
		cards.setCellValueFactory(new PropertyValueFactory<>("cards"));

/*        name.setStyle( "-fx-alignment: CENTER;");
        players.setStyle( "-fx-alignment: CENTER;");
        cards.setStyle( "-fx-alignment: CENTER;");*/

		sessionsTable.setPlaceholder(new Label("No sessions available"));

		sessionsTable.getItems().addAll(list);

		list.addListener((ListChangeListener<SessionObject>) change -> {
			System.out.println(list);
				while (change.next()) {
					if(change.wasAdded()){
						sessionsTable.getItems().addAll(change.getAddedSubList());
					}else if(change.wasRemoved()){
						sessionsTable.getItems().removeAll(change.getRemoved());
					}
				}
		});

		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(() -> handleRefresh(), 0, 1, TimeUnit.SECONDS);
	}

	/**
	 * Method loads a different FXML resource and create a new stage that will replace the current one, the
	 * {@link it.polimi.ingsw.Model.Player} will return to the Start Menu stage.
	 * @throws IOException
	 */
	public void handleBack() throws IOException {
		Platform.runLater(() -> {
			try {
				executor.shutdownNow();
				AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartMenu.fxml")));
				Scene scene = new Scene(pane, 715, 776);
				AppMain.window.setMinWidth(715);
				AppMain.window.setMinHeight(776);
				AppMain.window.setMaxWidth(715);
				AppMain.window.setMaxHeight(776);
				AppMain.window.setScene(scene);
			}catch(Exception e){}
		});
	}

	/**
	 * Method used to join a {@link it.polimi.ingsw.Server.Session}. The {@link MainController} notifies a {@link PlayerSelectSession}
	 * {@link Message}.
	 * @throws IOException
	 */
	public void handleJoin() throws IOException {

		executor.shutdownNow();

        String username = null;
        session = sessionsTable.getSelectionModel().getSelectedItem().name;
        UsernameDialog dialog = new UsernameDialog();
        username = dialog.display();

        String finalUsername = username;
        new Thread(() -> {
            if (finalUsername != null && finalUsername.length() >= 1) {
				mainController.notify(new PlayerSelectSession(session, finalUsername));
			}
		}).start();

		hourGlass1.setVisible(true);
		hourGlass2.setVisible(true);

		backButton.setDisable(true);
		joinButton.setDisable(true);

		RotateTransition rt1 = new RotateTransition(Duration.millis(2000), hourGlass1);
		RotateTransition rt2 = new RotateTransition(Duration.millis(2000), hourGlass2);

		rt1.setByAngle(360);
		rt1.setCycleCount(Animation.INDEFINITE);
		rt1.setInterpolator(Interpolator.LINEAR);


		rt2.setByAngle(360);
		rt2.setCycleCount(Animation.INDEFINITE);
		rt2.setInterpolator(Interpolator.LINEAR);

		rt1.play();
		rt2.play();
	}

	/**
	 * When the {@link MainController} receives a {@link it.polimi.ingsw.Messages.SuccessfulJoin} {@link Message} the
	 * {@link it.polimi.ingsw.Model.Player} will be redirected to a {@link WaitController} stage.
	 */
	public void handleStart() {
		Platform.runLater(() -> {
			try {
				if (!mainController.isPlaying()) {

					executor.shutdownNow();

					AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Wait.fxml")));
					Scene scene = new Scene(pane, 953, 511);
					AppMain.window.setMinWidth(953);
					AppMain.window.setMinHeight(511);
					AppMain.window.setMaxWidth(953);
					AppMain.window.setMaxHeight(511);
					AppMain.window.setScene(scene);
				}
			} catch (IOException ignored) {
			}
		});
	}

	/**
	 * If {@link MainController} receives a {@link InvalidUsernameException} this method will handle the exception asking
	 * the {@link it.polimi.ingsw.Model.Player} to re-insert a valid username.
	 * If {@link MainController} receives a {@link SessionNotExistsException} the stage will be reloaded via 'reload'
	 * method. This exception can occur in case the {@link it.polimi.ingsw.Model.Player} tries to join a {@link it.polimi.ingsw.Server.Session}.
	 * @param e
	 */
	public void handleException(Exception e) {
		if (e instanceof InvalidUsernameException) {
			Platform.runLater(() -> {
				try {
					String username = new UsernameDialog().displayError();
					if (username.length() > 0) {
						mainController.notify(new PlayerSelectSession(session, username));
					}
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			});
		}else if(e instanceof SessionNotExistsException){
			reload();
		}
	}

	/**
	 * Reloads the stage.
	 */
	private void reload(){
		Platform.runLater(() ->{
			AnchorPane pane = null;
			try {
				pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("JoinMenu.fxml")));
				Scene scene = new Scene(pane, 715, 776);
				AppMain.window.setMinWidth(715);
				AppMain.window.setMinHeight(776);
				AppMain.window.setMaxWidth(715);
				AppMain.window.setMaxHeight(776);
				AppMain.window.setScene(scene);
			} catch (IOException ignored) {
			}
		});
	}

	/**
	 * When {@link MainController} receives a {@link SessionListMessage} this method will parse the data and fill the
	 * {@link it.polimi.ingsw.Server.Session} table.
	 * @param msg
	 */
	public static void display(SessionListMessage msg) {
		HashMap<String, Integer> players = msg.getParticipants();
		HashMap<String, Boolean> cards = msg.getCards();
		ArrayList<String> names = getSessionNames();

		if(players.keySet().size() == 0){
			list.clear();
			System.out.println("Clearing list");
		}
		try {
			//deleting not-joinable sessions
			for (SessionObject o : list) {
				if (!players.containsKey(o.getName())) {
					list.remove(o);
				}
			}
			//adding new sessions
			for (String s : players.keySet()) {
				if (!names.contains(s)) {
					list.add(new SessionObject(s, players.get(s), cards.get(s)));
				}
			}
		}catch (Exception ign){}
	}

	/**
	 * Method to retrieve {@link it.polimi.ingsw.Server.Session} names retrieved by {@link SessionListMessage}.
	 * @return names
	 */
	private static ArrayList<String> getSessionNames(){
		ArrayList<String> names = new ArrayList<>();
		try {
			for (SessionObject o : list) {
				names.add(o.getName());
			}
		}catch(Exception e){}
		return names;
	}

	/**
	 * Method used by the {@link java.util.concurrent.ExecutorService} executor. The {@link MainController} notifies
	 * a {@link PlayerRetrieveSessions}.
	 */
	private void handleRefresh(){
		Message msg = new PlayerRetrieveSessions();
		mainController.notify(msg);
	}

}
