package it.polimi.ingsw.GUI;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class which represent the end match dialog.
 */
public class EndController{

	static Stage dialog = new Stage();

	/**
	 * If the parameter is true the dialog will display a Win message, a lost message will be displayed otherwise.
	 * @param win
	 * @throws IOException
	 */
	public void display(boolean win) throws IOException {

		AnchorPane pane;

		if(win){
			pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Win.fxml")));
		}else {
			pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Lose.fxml")));
		}
		Scene scene = new Scene(pane);
		dialog.setOnCloseRequest(e -> handleEnd());
		dialog.setMaxWidth(425);
		dialog.setMaxHeight(350);
		dialog.setMinHeight(350);
		dialog.setMinWidth(425);
		dialog.setScene(scene);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}

	/**
	 * Method triggered by clicking on "return to main menu". It loads a different FXML resource and create a new stage that will replace
	 * 	 * the current one.
	 */
	public void handleEnd(){
		dialog.close();
		Platform.runLater(() -> {
			try {
				AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartMenu.fxml")));
				Scene scene = new Scene(pane, 715, 776);
				AppMain.window.setMinWidth(715);
				AppMain.window.setMinHeight(776);
				AppMain.window.setMaxWidth(715);
				AppMain.window.setMaxHeight(776);
				AppMain.window.setScene(scene);
			}catch (IOException ignored){}
		});
	}

}
