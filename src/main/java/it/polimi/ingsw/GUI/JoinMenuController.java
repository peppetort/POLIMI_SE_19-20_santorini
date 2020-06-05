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

	ScheduledExecutorService executor;

	public static ObservableList<SessionObject> list = FXCollections.observableArrayList();

	public static void setMainController(MainController mc) {
		mainController = mc;
	}

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

	public void handleBack() throws IOException {
		executor.shutdownNow();
		AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartMenu.fxml")));
		Scene scene = new Scene(pane, 715, 776);
		AppMain.window.setMinWidth(715);
		AppMain.window.setMinHeight(776);
		AppMain.window.setMaxWidth(715);
		AppMain.window.setMaxHeight(776);
		AppMain.window.setScene(scene);
	}

	public void handleJoin() throws IOException {
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

	public void handleStart() {
		executor.shutdownNow();
		Platform.runLater(() -> {
			try {
				if (!mainController.isPlaying()) {
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

	public void handleException(Exception e) {
		if (e instanceof InvalidUsernameException) {
//            SessionObject obj;
//            obj = sessionsTable.getSelectionModel().getSelectedItem();
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

	private static ArrayList<String> getSessionNames(){
		ArrayList<String> names = new ArrayList<>();
		try {
			for (SessionObject o : list) {
				names.add(o.getName());
			}
		}catch(Exception e){}
		return names;
	}

	private void handleRefresh(){
		Message msg = new PlayerRetrieveSessions();
		mainController.notify(msg);
	}

}
