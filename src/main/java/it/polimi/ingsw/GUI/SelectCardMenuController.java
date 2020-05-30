package it.polimi.ingsw.GUI;

import it.polimi.ingsw.ClientGUIApp;
import it.polimi.ingsw.Messages.PlayerCardChoiceMessage;
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
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;


public class SelectCardMenuController implements Initializable {
	public ImageView godImage;
	public Label godName;
	public ToggleButton apollo;
	public AnchorPane apolloObscure;
	public ToggleButton artemis;
	public AnchorPane artemisObscure;
	public ToggleButton athena;
	public AnchorPane athenaObscure;
	public ToggleButton atlas;
	public AnchorPane atlasObscure;
	public ToggleButton demeter;
	public AnchorPane demeterObscure;
	public ToggleButton hephaestus;
	public AnchorPane hephaestusObscure;
	public ToggleButton minotaur;
	public AnchorPane minotaurObscure;
	public ToggleButton pan;
	public AnchorPane panObscure;
	public ToggleButton prometheus;
	public AnchorPane prometheusObscure;
	public Button addRemoveButton;
	public Button confirmButton;
	public Label actionType;
	public Text actionDescription;
	public ImageView powerIcon;

	public Scene scene;
	private God selected;
	private God chosen;
	private final HashMap<God, Boolean> godsChosen = new HashMap<>();

	private static MainController mainController = new MainController();

	public static void setMainController(MainController mc){mainController = mc;}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		ArrayList<God> deck = mainController.client.getStatus().getDeck();
		for(God g: deck){
			godsChosen.put(g, false);
		}

		for (God god : godsChosen.keySet()) {
			switch (god) {
				case APOLLO:
					apollo.setDisable(false);
					apolloObscure.setVisible(false);
					if (godsChosen.get(god)) {
						apollo.getStyleClass().remove("god-toggle");
						apollo.getStyleClass().add("god-toggle-chosen");
					}
					break;
				case ARTEMIS:
					artemis.setDisable(false);
					artemisObscure.setVisible(false);
					if (godsChosen.get(god)) {
						artemis.getStyleClass().remove("god-toggle");
						artemis.getStyleClass().add("god-toggle-chosen");
					}
					break;
				case ATHENA:
					athena.setDisable(false);
					athenaObscure.setVisible(false);
					if (godsChosen.get(god)) {
						athena.getStyleClass().remove("god-toggle");
						athena.getStyleClass().add("god-toggle-chosen");
					}
					break;
				case ATLAS:
					atlas.setDisable(false);
					atlasObscure.setVisible(false);
					if (godsChosen.get(god)) {
						atlas.getStyleClass().remove("god-toggle");
						atlas.getStyleClass().add("god-toggle-chosen");
					}
					break;
				case DEMETER:
					demeter.setDisable(false);
					demeterObscure.setVisible(false);
					if (godsChosen.get(god)) {
						demeter.getStyleClass().remove("god-toggle");
						demeter.getStyleClass().add("god-toggle-chosen");
					}
					break;
				case HEPHAESTUS:
					hephaestus.setDisable(false);
					hephaestusObscure.setVisible(false);
					if (godsChosen.get(god)) {
						hephaestus.getStyleClass().remove("god-toggle");
						hephaestus.getStyleClass().add("god-toggle-chosen");
					}
					break;
				case MINOTAUR:
					minotaur.setDisable(false);
					minotaurObscure.setVisible(false);
					if (godsChosen.get(god)) {
						minotaur.getStyleClass().remove("god-toggle");
						minotaur.getStyleClass().add("god-toggle-chosen");
					}
					break;
				case PAN:
					pan.setDisable(false);
					panObscure.setVisible(false);
					if (godsChosen.get(god)) {
						pan.getStyleClass().remove("god-toggle");
						pan.getStyleClass().add("god-toggle-chosen");
					}
					break;
				case PROMETHEUS:
					prometheus.setDisable(false);
					prometheusObscure.setVisible(false);
					if (godsChosen.get(god)) {
						prometheus.getStyleClass().remove("god-toggle");
						prometheus.getStyleClass().add("god-toggle-chosen");
					}
					break;
			}
		}

	}


	public void handleSelect(ActionEvent actionEvent) {

		selected = God.valueOf(((ToggleButton) actionEvent.getSource()).getId().toUpperCase());

		try {
			GodObject god = new GodObject(selected);
			this.godImage.setImage(god.getCardGodImage());
			this.powerIcon.setImage(god.getPowerIconImage());
			this.actionType.setText(god.getActionTypeLabel());
			this.actionDescription.setText(god.getActionDescriptionLabel());
			this.godName.setText(god.getGodNameLabel());
		}catch (NullPointerException e){
			e.printStackTrace();
		}

		if(!godsChosen.get(selected)) {
			if (chosen == null) {
				addRemoveButton.setVisible(true);
				addRemoveButton.getStyleClass().remove("join-button");
				addRemoveButton.getStyleClass().remove("create-button");
				addRemoveButton.getStyleClass().add("create-button");
				addRemoveButton.setText("Chose");
			} else if (selected.equals(chosen)) {
				addRemoveButton.setVisible(true);
				addRemoveButton.getStyleClass().remove("create-button");
				addRemoveButton.getStyleClass().remove("join-button");
				addRemoveButton.getStyleClass().add("join-button");
				addRemoveButton.setText("Remove");
			} else {
				addRemoveButton.setVisible(false);
			}
		}else {
			addRemoveButton.setVisible(false);
		}

	}

	public void handleAdd(ActionEvent actionEvent) {
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

		if (chosen != null && chosen.equals(selected)) {
			chosen = null;
			godButton.getStyleClass().remove("god-selected");
			godButton.getStyleClass().add("god-toggle");
			addRemoveButton.getStyleClass().remove("join-button");
			addRemoveButton.getStyleClass().remove("create-button");
			addRemoveButton.getStyleClass().add("create-button");
			addRemoveButton.setText("Chose");
		} else {
			chosen = selected;
			godButton.getStyleClass().remove("god-toggle");
			godButton.getStyleClass().add("god-selected");
			addRemoveButton.getStyleClass().remove("create-button");
			addRemoveButton.getStyleClass().remove("join-button");
			addRemoveButton.getStyleClass().add("join-button");
			addRemoveButton.setText("Remove");
		}

		confirmButton.setVisible(chosen != null);
	}


	public void handleConfirm() {
		mainController.notify(new PlayerCardChoiceMessage(selected));
		mainController.setCard(selected);
		Platform.runLater(() -> {
			try{
				AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PlayingStage.fxml")));
				Scene scene = new Scene(pane, 1166, 778);
				ClientGUIApp.window.setScene(scene);
			}catch (IOException ignored){}
		});
	}
}
