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

/**
 * Dialog used to insert the player's username.
 */
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

    /**
     * Display the dialog and returns the inserted value.
     * @return
     * @throws IOException
     */
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

    /**
     * Display the dialog in case of an {@link it.polimi.ingsw.Exceptions.InvalidUsernameException} and returns the inserted value.
     * @return
     * @throws IOException
     */
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

    /**
     * When you press the confirm {@link Button} the dialog close itself.
     */
    @FXML
    private void handleConfirm(){
            text = username.getText();
            dialog.close();
    }

}
