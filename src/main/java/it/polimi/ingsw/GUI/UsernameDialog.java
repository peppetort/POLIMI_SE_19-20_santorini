package it.polimi.ingsw.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class UsernameDialog {

    static Stage dialog = new Stage();
    public TextField username;
    public Button confirmButton;
    String text;

    public String display() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("UsernameDialog.fxml")));
        Scene scene = new Scene(pane);
        //dialog.setResizable(false);
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);

        confirmButton = (Button) scene.lookup("#confirmButton");
        username = (TextField) scene.lookup("#username");

        confirmButton.setOnAction(e -> {
            text = this.username.getText();
            dialog.close();
        });

        dialog.showAndWait();
        return text;
    }

}
