package it.polimi.ingsw.GUI;


import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * When a player is waiting for other players to join the session it will be placed in a waiting stage until the {@link it.polimi.ingsw.Model.Game}
 * starts. This is the controller for the Wait.fxml.
 */
public class WaitController implements Initializable {
	public ImageView bottomBoat;
	public ImageView rightCloud;
	public ImageView hourGlass1;
	public ImageView hourGlass2;

	/**
	 * Loads the animations.
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		TranslateTransition tt1 = new TranslateTransition(Duration.millis(15000), bottomBoat);
		TranslateTransition tt2 = new TranslateTransition(Duration.millis(15000), rightCloud);

		RotateTransition rt1 = new RotateTransition(Duration.millis(2000), hourGlass1);
		RotateTransition rt2 = new RotateTransition(Duration.millis(2000), hourGlass2);

		rt1.setByAngle(360);
		rt1.setCycleCount(Animation.INDEFINITE);
		rt1.setInterpolator(Interpolator.LINEAR);


		rt2.setByAngle(360);
		rt2.setCycleCount(Animation.INDEFINITE);
		rt2.setInterpolator(Interpolator.LINEAR);


		tt1.setToX(-150);
		tt1.setByY(100);
		tt1.setCycleCount(Timeline.INDEFINITE);

		tt2.setToX(-100);
		tt2.setToY(10);
		tt2.setCycleCount(Timeline.INDEFINITE);

		tt1.play();
		tt2.play();


		rt1.play();
		rt2.play();

	}

	/**
	 * When the {@link it.polimi.ingsw.Model.Game} starts and the {@link it.polimi.ingsw.Client.Client} receives a
	 * {@link it.polimi.ingsw.Messages.ClientInitMessage} this method will load PlayingStage.fxml scene handled by
	 * {@link PlayingStageController}.
	 */
	public void handleStart(){
		Platform.runLater(() ->{
			try {
					AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PlayingStage.fxml")));
				Scene scene = new Scene(pane, 1219, 789);
				AppMain.window.setMaxHeight(2000);
				AppMain.window.setMaxWidth(2000);
				AppMain.window.setMinHeight(789);
				AppMain.window.setMinWidth(1219);
				AppMain.window.setScene(scene);
			}catch (IOException ignored){}
		});
	}



}
