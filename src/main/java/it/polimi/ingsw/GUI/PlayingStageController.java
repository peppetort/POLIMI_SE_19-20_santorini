package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Box;
import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.ClientGUIApp;
import it.polimi.ingsw.Messages.ChatUpdateMessage;
import it.polimi.ingsw.Messages.InvalidChoiceMessage;
import it.polimi.ingsw.Messages.PlayerChatMessage;
import it.polimi.ingsw.Model.Actions;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlayingStageController implements Initializable {

	public GridPane boardPane;
	public GridPane actionPane;
	public GridPane pawnPane;

	public Label placeLabel;
	public Label endLabel;
	public Label deckLabel;
	public Label cardLabel;
	public Label moveLabel;
	public Label buildLabel;
	public Label buildDomeLabel;
	public Label selectLabel;
	public Label undoLabel;

	public Button endButton;
	public Button undoButton;

	public AnchorPane godInfo;
	public ImageView podiumGod;
	public ImageView powerIcon;
	public Label godName;
	public Label actionType;
	public Text actionDescription;

	private String godNameLabel;
	private Image podiumGodImage;
	private Image powerIconImage;
	private String actionTypeLabel;
	private String actionDescriptionLabel;

	private boolean open = false;

	public TextArea chatField;
	public TextField chatText;
	public Button sendButton;

	private JSONObject jsonObject;

	private static MainController mainController;

	static MenuButton[][] menu = new MenuButton[5][5];

	static Building[][] buildings;

	static Pawn[][] greenPawns;
	static Pawn[][] bluePawns;
	static Pawn[][] redPawns;

	static MenuItem[][] place;
	static MenuItem[][] build;
	static MenuItem[][] select;
	static MenuItem[][] move;

	protected static int x, y;


	ActionsHandler actionsHandler = new ActionsHandler(mainController, this);

	static ObservableList<Actions> list = FXCollections.observableArrayList();
	static ObservableList<String> messages = FXCollections.observableArrayList();

	public static void setMainController(MainController mc) {
		mainController = mc;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		if (godNameLabel != null) {
			godName.setText(godNameLabel);
			actionType.setText(actionTypeLabel);
			actionDescription.setText(actionDescriptionLabel);
			podiumGod.setImage(podiumGodImage);
			powerIcon.setImage(powerIconImage);
		} else {
			godName.setText(mainController.client.getStatus().getUsername());
			actionType.setText("Default Turn");
			actionDescription.setText("Your worker must move to an adjacent space and then build");
		}

		chatField.setWrapText(true);

		podiumGod.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			TranslateTransition transition = new TranslateTransition(Duration.millis(500), godInfo);

			if (open) {
				transition.setToX(0);
				open = false;
			} else {
				transition.setToX(200);
				open = true;
			}

			transition.play();
			event.consume();
		});

		mainController.setPlaying(true);

		messages.addListener((ListChangeListener.Change<? extends String> change) -> {
			if (change.next()) {
				chatField.appendText(change.getAddedSubList().get(0));
			}
		});

		undoButton.setOnAction(actionsHandler::handleUndo);
		endButton.setOnAction(actionsHandler::handleEnd);

		place = new MenuItem[5][5];
		move = new MenuItem[5][5];
		build = new MenuItem[5][5];
		select = new MenuItem[5][5];

		redPawns = new Pawn[5][5];
		bluePawns = new Pawn[5][5];
		greenPawns = new Pawn[5][5];

		buildings = new Building[5][5];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				place[i][j] = new MenuItem("Place");
				place[i][j].setOnAction(actionsHandler::handlePlace);

				build[i][j] = new MenuItem("Build");
				build[i][j].setOnAction(actionsHandler::handleBuild);

				select[i][j] = new MenuItem("Select");
				select[i][j].setOnAction(actionsHandler::handleSelect);

				move[i][j] = new MenuItem("Move");
				move[i][j].setOnAction(actionsHandler::handleMove);

				redPawns[i][j] = new Pawn(Color.RED);
				redPawns[i][j].setOpacity(0);
				greenPawns[i][j] = new Pawn(Color.GREEN);
				greenPawns[i][j].setOpacity(0);
				bluePawns[i][j] = new Pawn(Color.BLUE);
				bluePawns[i][j].setOpacity(0);

				buildings[i][j] = new Building();

			}
		}


		try {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {

					menu[i][j] = new MenuButton();
					menu[i][j].setPrefHeight(80);
					menu[i][j].setPrefWidth(80);
					menu[i][j].setOpacity(0);
					menu[i][j].setPopupSide(Side.RIGHT);
					menu[i][j].setOnMouseEntered(this::handleMouseOver);
					menu[i][j].setOnMouseExited(this::handleMouseExit);
					menu[i][j].setOnMouseClicked(this::handleAction);

					GridPane.setConstraints(menu[i][j], j, i);

					GridPane.setConstraints(redPawns[i][j], j, i);
					GridPane.setConstraints(bluePawns[i][j], j, i);
					GridPane.setConstraints(greenPawns[i][j], j, i);

					GridPane.setConstraints(buildings[i][j], j, i);

					actionPane.getChildren().add(menu[i][j]);

					pawnPane.getChildren().addAll(redPawns[i][j], greenPawns[i][j], bluePawns[i][j]);

					boardPane.getChildren().add(buildings[i][j]);
				}
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}

		list.addListener((ListChangeListener.Change<? extends Actions> change) -> {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					menu[i][j].getItems().clear();
				}
			}
			if (change.next()) {
				for (Actions a : Actions.values()) {
					if (list.contains(a) && change.wasAdded()) {
						switch (a) {
							case PLACE:
								placeLabel.getStyleClass().remove("actionLabel");
								placeLabel.getStyleClass().add("actionLabelSelected");

								for (int i = 0; i < 5; i++) {
									for (int j = 0; j < 5; j++) {
										menu[i][j].getItems().add(place[i][j]);
									}
								}

								break;
							case BUILD:
								buildLabel.getStyleClass().remove("actionLabel");
								buildLabel.getStyleClass().add("actionLabelSelected");
								for (int i = 0; i < 5; i++) {
									for (int j = 0; j < 5; j++) {
										menu[i][j].getItems().add(build[i][j]);
									}
								}
								break;
							case MOVE:
								moveLabel.getStyleClass().remove("actionLabel");
								moveLabel.getStyleClass().add("actionLabelSelected");
								for (int i = 0; i < 5; i++) {
									for (int j = 0; j < 5; j++) {
										menu[i][j].getItems().add(move[i][j]);
									}
								}
								break;
							case DECK:
								deckLabel.getStyleClass().remove("actionLabel");
								deckLabel.getStyleClass().add("actionLabelSelected");
								Platform.runLater(() -> {
									try {
										AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AllCardsMenu.fxml")));
										Scene scene = new Scene(pane, 953, 511);
										ClientGUIApp.window.setScene(scene);
									} catch (IOException ignored) {
									}
								});
								break;
							case CARD:
								cardLabel.getStyleClass().remove("actionLabel");
								cardLabel.getStyleClass().add("actionLabelSelected");
								Platform.runLater(() -> {
									try {
										AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SelectCardMenu.fxml")));
										Scene scene = new Scene(pane, 953, 511);
										ClientGUIApp.window.setScene(scene);
									} catch (IOException ignored) {
									}
								});
								break;
							case END:
								endLabel.getStyleClass().remove("actionLabel");
								endLabel.getStyleClass().add("actionLabelSelected");
								break;
							case SELECT:
								selectLabel.getStyleClass().remove("actionLabel");
								selectLabel.getStyleClass().add("actionLabelSelected");
								for (int i = 0; i < 5; i++) {
									for (int j = 0; j < 5; j++) {
										menu[i][j].getItems().add(select[i][j]);
									}
								}
								break;
							case UNDO:
								undoLabel.getStyleClass().remove("actionLabel");
								undoLabel.getStyleClass().add("actionLabelSelected");
								break;
							case BUILD_DOME:
								buildDomeLabel.getStyleClass().remove("actionLabel");
								buildDomeLabel.getStyleClass().add("actionLabelSelected");
								break;
						}
					} else if (change.wasRemoved() || !list.contains(a)) {
						switch (a) {
							case PLACE:
								placeLabel.getStyleClass().remove("actionLabelSelected");
								placeLabel.getStyleClass().add("actionLabel");
								break;
							case BUILD:
								buildLabel.getStyleClass().remove("actionLabelSelected");
								buildLabel.getStyleClass().add("actionLabel");
								break;
							case MOVE:
								moveLabel.getStyleClass().remove("actionLabelSelected");
								moveLabel.getStyleClass().add("actionLabel");
								break;
							case DECK:
								deckLabel.getStyleClass().remove("actionLabelSelected");
								deckLabel.getStyleClass().add("actionLabel");
								break;
							case CARD:
								cardLabel.getStyleClass().remove("actionLabelSelected");
								cardLabel.getStyleClass().add("actionLabel");
								break;
							case END:
								endLabel.getStyleClass().remove("actionLabelSelected");
								endLabel.getStyleClass().add("actionLabel");
								break;
							case SELECT:
								selectLabel.getStyleClass().remove("actionLabelSelected");
								selectLabel.getStyleClass().add("actionLabel");
								break;
							case UNDO:
								undoLabel.getStyleClass().remove("actionLabelSelected");
								undoLabel.getStyleClass().add("actionLabel");
								break;
							case BUILD_DOME:
								buildDomeLabel.getStyleClass().remove("actionLabelSelected");
								buildDomeLabel.getStyleClass().add("actionLabel");
								break;
						}

					}
				}
			}
		});

	}


	public static void setActionLabel(ArrayList<Actions> act) {
		Platform.runLater(() -> {
			try {
				list.clear();
				list.addAll(act);
			} catch (NullPointerException e) {
				System.out.print("lista vuota");
			}
		});
	}

	public void handleAction(javafx.scene.input.MouseEvent e) {

		//NEL MODEL LA X E' LA RIGA E Y LA COLONNA

		x = GridPane.getRowIndex((Node) e.getSource());
		y = GridPane.getColumnIndex((Node) e.getSource());

		e.consume();
	}


	public void handleMouseOver(javafx.scene.input.MouseEvent e) {
		((MenuButton) e.getSource()).setOpacity(0.4);
	}


	public void handleMouseExit(javafx.scene.input.MouseEvent e) {
		((MenuButton) e.getSource()).setOpacity(0);
	}

	public static void updateBoard() {
		Client client = mainController.client;

		//box ha il livello e il colore della pedina
		// box get level e get player
		try {
			Box[][] boardModel = client.getBoard().getBoard();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {


					buildings[i][j].build(boardModel[i][j].getLevel());

					if (boardModel[i][j].getPlayer() != null) {
						switch (boardModel[i][j].getPlayer()) {
							case BLUE:
								bluePawns[i][j].setOpacity(1);
								redPawns[i][j].setOpacity(0);
								greenPawns[i][j].setOpacity(0);
								break;
							case RED:
								bluePawns[i][j].setOpacity(0);
								redPawns[i][j].setOpacity(1);
								greenPawns[i][j].setOpacity(0);
								break;
							case GREEN:
								bluePawns[i][j].setOpacity(0);
								redPawns[i][j].setOpacity(0);
								greenPawns[i][j].setOpacity(1);
								break;
							default:
								bluePawns[i][j].setOpacity(0);
								redPawns[i][j].setOpacity(0);
								greenPawns[i][j].setOpacity(0);
								break;
						}
					} else {
						bluePawns[i][j].setOpacity(0);
						redPawns[i][j].setOpacity(0);
						greenPawns[i][j].setOpacity(0);
					}

				}
			}
		} catch (NullPointerException ignored) {
		}

	}

	@FXML
	private void handleSend() {
		mainController.notify(new PlayerChatMessage(chatText.getText()));
		chatText.setText("");
	}

	public static void handleChatUpdate(ChatUpdateMessage msg) {
		messages.add(msg.getMessage());
	}

	public static void handleException(InvalidChoiceMessage message) {
		updateBoard();
		messages.add("Error: " + message.getMessage() + "\n");
	}

	public void handleCardChoice(God selected) {
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

		JSONObject actionType = (JSONObject) jsonObject.get(selected.toString());
		JSONObject actionDescription = (JSONObject) jsonObject.get(selected.toString());

		godNameLabel = selected.toString();
		actionTypeLabel = actionType.get("type").toString();
		actionDescriptionLabel = actionDescription.get("description").toString();

		switch (selected) {
			case APOLLO:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Apolo.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0044_god_and_hero_powers0014.png")).toExternalForm());
				break;
			case ARTEMIS:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Artemis.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0048_god_and_hero_powers0010.png")).toExternalForm());
				break;
			case ATHENA:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Athena.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0050_god_and_hero_powers0008.png")).toExternalForm());
				break;
			case ATLAS:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Atlas.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0049_god_and_hero_powers0009.png")).toExternalForm());
				break;
			case DEMETER:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Demeter.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0052_god_and_hero_powers0006.png")).toExternalForm());
				break;
			case HEPHAESTUS:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Hephaestus.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0052_god_and_hero_powers0006.png")).toExternalForm());
				break;
			case MINOTAUR:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Minotaur.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0009_god_and_hero_powers0049.png")).toExternalForm());
				break;
			case PAN:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Pan.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0056_god_and_hero_powers0002.png")).toExternalForm());
				break;
			case PROMETHEUS:
				podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Prometheus.png")).toExternalForm());
				powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0005_god_and_hero_powers0053.png")).toExternalForm());
				break;
			default:
				podiumGodImage = null;
				powerIconImage = null;
		}
	}

}
