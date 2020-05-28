package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Model.God;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
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
	private JSONObject jsonObject;
	private God selected;
	private God chosen;
	private final HashMap<God, Boolean> godsChosen = new HashMap<>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		godsChosen.put(God.APOLLO, true);
		godsChosen.put(God.PAN, false);
		godsChosen.put(God.ATHENA, false);

		JSONParser parser = new JSONParser();

		try {
			URL jsonURL = getClass().getClassLoader().getResource("gods.json");
			assert jsonURL != null;
			File file = new File(jsonURL.getFile());
			String path = URLDecoder.decode(file.toString(), "UTF-8");

			jsonObject = (JSONObject) parser.parse(new FileReader(path));

		} catch (ParseException | IOException e) {
			e.printStackTrace();
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
		Image godImage;
		Image powerIcon;

		JSONObject actionType = (JSONObject) jsonObject.get(selected.toString());
		JSONObject actionDescription = (JSONObject) jsonObject.get(selected.toString());

		godName.setText(selected.toString());
		this.actionType.setText(actionType.get("type").toString());
		this.actionDescription.setText(actionDescription.get("description").toString());

		switch (selected) {
			case APOLLO:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0043_god_and_hero_cards_0013_apollo.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0044_god_and_hero_powers0014.png")).toExternalForm());
				break;
			case ARTEMIS:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0054_god_and_hero_cards_0002_Artemis.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0048_god_and_hero_powers0010.png")).toExternalForm());
				break;
			case ATHENA:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0052_god_and_hero_cards_0004_Athena.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0050_god_and_hero_powers0008.png")).toExternalForm());
				break;
			case ATLAS:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0053_god_and_hero_cards_0003_Atlas.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0049_god_and_hero_powers0009.png")).toExternalForm());
				break;
			case DEMETER:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0050_god_and_hero_cards_0006_Demeter.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0052_god_and_hero_powers0006.png")).toExternalForm());
				break;
			case HEPHAESTUS:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0009_god_and_hero_cards_0047_Hephaestus.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0052_god_and_hero_powers0006.png")).toExternalForm());
				break;
			case MINOTAUR:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0008_god_and_hero_cards_0048_Minotaur.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0009_god_and_hero_powers0049.png")).toExternalForm());
				break;
			case PAN:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0046_god_and_hero_cards_0010_Pan.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0056_god_and_hero_powers0002.png")).toExternalForm());
				break;
			case PROMETHEUS:
				godImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0004_god_and_hero_cards_0052_Prometheus.png")).toExternalForm());
				powerIcon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0005_god_and_hero_powers0053.png")).toExternalForm());
				break;
			default:
				godImage = null;
				powerIcon = null;
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

		this.godImage.setImage(godImage);
		this.powerIcon.setImage(powerIcon);

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


	public God handleConfirm(ActionEvent actionEvent) {
		return chosen;
		//TODO: implementare la logica
	}
}
