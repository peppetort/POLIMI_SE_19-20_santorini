package it.polimi.ingsw.GUI;


import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.ClientGUIApp;
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


public class WaitController implements Initializable {
	public ImageView bottomBoat;
	public ImageView rightCloud;
	public ImageView hourGlass1;
	public ImageView hourGlass2;

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

	public void handleStart(){
		Platform.runLater(() ->{
			try {
					AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("PlayingStage.fxml")));
				Scene scene = new Scene(pane, 1219, 789);
				ClientGUIApp.window.setMaxHeight(2000);
				ClientGUIApp.window.setMaxWidth(2000);
				ClientGUIApp.window.setMinHeight(789);
				ClientGUIApp.window.setMinWidth(1219);
				ClientGUIApp.window.setScene(scene);
			}catch (IOException ignored){}
		});
	}



}
