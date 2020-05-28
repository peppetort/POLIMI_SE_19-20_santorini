package it.polimi.ingsw.GUI;

import it.polimi.ingsw.ClientGUIApp;
import it.polimi.ingsw.Exceptions.AlreadyExistingSessionException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PlayerCreateSessionMessage;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateMenuController implements Initializable {

	public static MainController mainController;

	public TextField sessionName;
	public TextField username;
	public ToggleButton cardBox;
	public RadioButton playerButton2;
	public RadioButton playerButton3;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		playerButton2.setSelected(true);
		//cardBox.getItems().addAll("Yes","No");
		//cardBox.getSelectionModel().selectFirst();
		//playerBox.getItems().addAll(2,3);
		//playerBox.getSelectionModel().selectFirst();

	}

	public static void setMainController(MainController mc) {
		mainController = mc;
	}

	public void handleBack() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("StartMenu.fxml"));
		Scene scene = new Scene(pane, 715, 776);
		ClientGUIApp.window.setScene(scene);
	}

	public void handleCreate(){
		boolean simple;
		int playersNumber;

		simple = !cardBox.isSelected();

		if (playerButton2.isSelected()) {
			playersNumber = 2;
		} else {
			playersNumber = 3;
		}


		Message msg = new PlayerCreateSessionMessage(username.getText(), sessionName.getText(), playersNumber, simple);
		mainController.notify(msg);
	}

	public static void handleException(Exception msg) {

		if (msg instanceof AlreadyExistingSessionException) {

			//TODO : SEPARARE IN UNA CLASSE :)
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Look, an Error Dialog");
				alert.setContentText("Ooops, there was an error!");
				alert.showAndWait();
			});
		}
	}

	public void handleStart() {
		Platform.runLater(() -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("PlayingStage.fxml"));
				Scene scene = new Scene(pane, 1280, 720);
				ClientGUIApp.window.setScene(scene);
			} catch (IOException e) {
			}
		});
	}

}
