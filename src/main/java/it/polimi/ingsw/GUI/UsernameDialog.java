package it.polimi.ingsw.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UsernameDialog implements Initializable {

    static Stage dialog = new Stage();
    static String text;

    static SimpleStringProperty prompt = new SimpleStringProperty();

    public Label promptedText;
    public TextField username;
    public Button confirmButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        promptedText.textProperty().bind(prompt);
    }

    public String display() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("UsernameDialog.fxml")));
        Scene scene = new Scene(pane);
        prompt.setValue("Insert an username:");
        dialog.setMaxWidth(260);
        dialog.setMaxHeight(360);
        dialog.setMinHeight(360);
        dialog.setMinWidth(260);
        dialog.setScene(scene);
//        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
        return text;
    }

    public String displayError() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("UsernameDialog.fxml")));
        Scene scene = new Scene(pane);
        prompt.setValue("Username already in use.");
        dialog.setResizable(false);
        dialog.setScene(scene);
//        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
        return text;
    }

    @FXML
    private void handleConfirm(){
            text = username.getText();
            dialog.close();
    }

}
