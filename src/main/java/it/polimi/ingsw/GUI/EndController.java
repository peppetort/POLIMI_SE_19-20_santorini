package it.polimi.ingsw.GUI;


import it.polimi.ingsw.ClientGUIApp;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class EndController{

	static Stage dialog = new Stage();

	public void display(boolean win) throws IOException {

		AnchorPane pane;

		if(win){
			pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Win.fxml")));
		}else {
			pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Lose.fxml")));
		}
		Scene scene = new Scene(pane);
		dialog.setMaxWidth(425);
		dialog.setMaxHeight(350);
		dialog.setMinHeight(350);
		dialog.setMinWidth(425);
		dialog.setScene(scene);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}

	public void handleEnd(){
		dialog.close();
		Platform.runLater(() -> {
			try {
				AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartMenu.fxml")));
				Scene scene = new Scene(pane, 715, 776);
				ClientGUIApp.window.setMinWidth(715);
				ClientGUIApp.window.setMinHeight(776);
				ClientGUIApp.window.setMaxWidth(715);
				ClientGUIApp.window.setMaxHeight(776);
				ClientGUIApp.window.setScene(scene);
			}catch (IOException ignored){}
		});
	}

}
