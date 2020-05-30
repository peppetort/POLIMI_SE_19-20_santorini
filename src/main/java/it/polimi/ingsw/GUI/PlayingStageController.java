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

import java.io.IOException;
import java.net.URL;
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

	private static String godNameLabel;
	private static Image podiumGodImage;
	private static Image powerIconImage;
	private static String actionTypeLabel;
	private static String actionDescriptionLabel;

	private boolean open = false;

	public TextArea chatField;
	public TextField chatText;
	public Button sendButton;

	private static MainController mainController;

	static MenuButton[][] menu = new MenuButton[5][5];

	static Building[][] buildings;
	static Pawn[][] pawns;
	static MenuItem[][] actions;

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

		actions = new MenuItem[5][5];
		pawns = new Pawn[5][5];
		buildings = new Building[5][5];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				pawns[i][j] = new Pawn();
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
					GridPane.setConstraints(pawns[i][j], j, i);
					GridPane.setConstraints(buildings[i][j], j, i);

					actionPane.getChildren().add(menu[i][j]);
					pawnPane.getChildren().add(pawns[i][j]);
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
										actions[i][j] = new MenuItem("Place");
										actions[i][j].setOnAction(actionsHandler::handlePlace);
										menu[i][j].getItems().add(actions[i][j]);
									}
								}

								break;
							case BUILD:
								buildLabel.getStyleClass().remove("actionLabel");
								buildLabel.getStyleClass().add("actionLabelSelected");
								for (int i = 0; i < 5; i++) {
									for (int j = 0; j < 5; j++) {
										actions[i][j] = new MenuItem("Build");
										actions[i][j].setOnAction(actionsHandler::handleBuild);
										menu[i][j].getItems().add(actions[i][j]);
									}
								}
								break;
							case MOVE:
								moveLabel.getStyleClass().remove("actionLabel");
								moveLabel.getStyleClass().add("actionLabelSelected");
								for (int i = 0; i < 5; i++) {
									for (int j = 0; j < 5; j++) {
										actions[i][j] = new MenuItem("Move");
										actions[i][j].setOnAction(actionsHandler::handleMove);
										menu[i][j].getItems().add(actions[i][j]);
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
										actions[i][j] = new MenuItem("Select");
										actions[i][j].setOnAction(actionsHandler::handleSelect);
										menu[i][j].getItems().add(actions[i][j]);
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
					pawns[i][j].setColor(boardModel[i][j].getPlayer());
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

	public static void handleCardChoice(God selected) {

		GodObject god = new GodObject(selected);
		godNameLabel = god.getGodNameLabel();
		podiumGodImage = god.getPodiumGodImage();
		powerIconImage = god.getPowerIconImage();
		actionTypeLabel = god.getActionTypeLabel();
		actionDescriptionLabel = god.getActionDescriptionLabel();

	}

}
