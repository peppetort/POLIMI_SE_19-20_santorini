package it.polimi.ingsw.GUI;

import it.polimi.ingsw.ClientGUIApp;
import it.polimi.ingsw.Messages.PlayerDeckMessage;
import it.polimi.ingsw.Model.God;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class AllCardsMenuController implements Initializable {
	public ImageView godImage;
	public Label godName;
	public ToggleButton apollo;
	public ToggleButton artemis;
	public ToggleButton athena;
	public ToggleButton atlas;
	public ToggleButton demeter;
	public ToggleButton hephaestus;
	public ToggleButton minotaur;
	public ToggleButton pan;
	public ToggleButton prometheus;
	public Button addRemoveButton;
	public Button confirmButton;
	public Label actionType;
	public Text actionDescription;
	public ImageView powerIcon;


	public God selected;
	public ArrayList<God> added = new ArrayList<>();
	public final int playersNumber = mainController.client.getStatus().getPlayersNumber();

	private static MainController mainController = new MainController();

	public Scene scene;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	public static void setMainController(MainController mc){
		mainController = mc;
	}


	public void handleSelect(ActionEvent actionEvent) {
		selected = God.valueOf(((ToggleButton) actionEvent.getSource()).getId().toUpperCase());

		try{
			GodObject god = new GodObject(selected);
			this.godImage.setImage(god.getCardGodImage());
			this.powerIcon.setImage(god.getPowerIconImage());
			this.actionType.setText(god.getActionTypeLabel());
			this.actionDescription.setText(god.getActionDescriptionLabel());
			this.godName.setText(god.getGodNameLabel());
		}catch (NullPointerException e){
			e.printStackTrace();
		}

		if (added.contains(selected)) {
			addRemoveButton.setVisible(true);
			addRemoveButton.getStyleClass().remove("create-button");
			addRemoveButton.getStyleClass().remove("join-button");
			addRemoveButton.getStyleClass().add("join-button");
			addRemoveButton.setText("Remove");
		} else {
			if (added.size() == playersNumber) {
				addRemoveButton.setVisible(false);
			} else {
				addRemoveButton.setVisible(true);
				addRemoveButton.getStyleClass().remove("join-button");
				addRemoveButton.getStyleClass().remove("create-button");
				addRemoveButton.getStyleClass().add("create-button");
				addRemoveButton.setText("Add");
			}
		}

	}

	public void handleAdd() {
		ToggleButton godButton;

		switch (selected) {
			case APOLLO:
				godButton = apollo;
				break;
			case ARTEMIS:
				godButton = artemis;
				break;
			case ATHENA:
				godButton = athena;
				break;
			case ATLAS:
				godButton = atlas;
				break;
			case DEMETER:
				godButton = demeter;
				break;
			case HEPHAESTUS:
				godButton = hephaestus;
				break;
			case MINOTAUR:
				godButton = minotaur;
				break;
			case PAN:
				godButton = pan;
				break;
			case PROMETHEUS:
				godButton = prometheus;
				break;
			default:
				godButton = null;
		}

		if (added.contains(selected)) {
			added.remove(selected);
			godButton.getStyleClass().remove("god-selected");
			godButton.getStyleClass().add("god-toggle");
			addRemoveButton.getStyleClass().remove("join-button");
			addRemoveButton.getStyleClass().remove("create-button");
			addRemoveButton.getStyleClass().add("create-button");
			addRemoveButton.setText("Add");
		} else {
			added.add(selected);
			godButton.getStyleClass().remove("god-toggle");
			godButton.getStyleClass().add("god-selected");
			addRemoveButton.getStyleClass().remove("create-button");
			addRemoveButton.getStyleClass().remove("join-button");
			addRemoveButton.getStyleClass().add("join-button");
			addRemoveButton.setText("Remove");
		}

		confirmButton.setVisible(added.size() == playersNumber);
	}


	public void handleConfirm() {
		mainController.notify(new PlayerDeckMessage(added));
		Platform.runLater(() -> {
			try {
				AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PlayingStage.fxml")));
				Scene scene = new Scene(pane, 1219, 789);
				ClientGUIApp.window.setMaxHeight(2000);
				ClientGUIApp.window.setMaxWidth(2000);
				ClientGUIApp.window.setMinHeight(789);
				ClientGUIApp.window.setMinWidth(1219);
				ClientGUIApp.window.setScene(scene);
			}catch(IOException ignored){}
		});
	}
}
