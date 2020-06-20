package it.polimi.ingsw.GUI;

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

/**
 * Controller class for the AllCards stage that will be diplayed when the challenger has to choose the available cards for
 * each players.
 */
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

	/**
	 * Method triggered by a MouseClick over a card. The "selected" attribute becomes the {@link God} value and then two
	 * buttons (Add or Remove) will be displayed to add the card or remove from deck.
	 * @param actionEvent
	 */
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

	/**
	 * Triggered by a MouseClick over 'Add' button. Adds the selected {@link God} to 'added' {@link ArrayList}. When
	 * the size of such list becomes equal to the number of players the stage will show a confirm button to send the
	 * choosen deck.
	 */
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

	/**
	 * {@link MainController} will notify a {@link PlayerDeckMessage} where the deck represents the choosen cards.
	 */
	public void handleConfirm() {
		mainController.notify(new PlayerDeckMessage(added));
		Platform.runLater(() -> {
			try {
				AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PlayingStage.fxml")));
				Scene scene = new Scene(pane, 1219, 789);
				AppMain.window.setMaxHeight(2000);
				AppMain.window.setMaxWidth(2000);
				AppMain.window.setMinHeight(789);
				AppMain.window.setMinWidth(1219);
				AppMain.window.setScene(scene);
			}catch(IOException ignored){}
		});
	}
}
