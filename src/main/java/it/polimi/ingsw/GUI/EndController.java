package it.polimi.ingsw.GUI;


import it.polimi.ingsw.ClientGUIApp;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EndController implements Initializable {

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	public void handleEnd(){
		Platform.runLater(() -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("StartMenu.fxml"));
				Scene scene = new Scene(pane, 1280, 720);
				ClientGUIApp.window.setScene(scene);
			}catch (IOException e){}
		});
	}

}
