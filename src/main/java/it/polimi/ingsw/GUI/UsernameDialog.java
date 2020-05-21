package it.polimi.ingsw.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UsernameDialog {

    static Stage dialog;
    static Scene scene;
    static VBox layout;
    static Button button;
    static Label label;
    static TextField text;
    static String username;

    public static String display(String message){

        dialog = new Stage();
        layout = new VBox();
        scene = new Scene(layout,400,400);
        button = new Button("Confirm");
        label = new Label(message);
        text = new TextField();

        layout.getChildren().addAll(label,text,button);
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);

        button.setOnAction(e -> {
            username = text.getText();
            dialog.close();
        });

        dialog.showAndWait();
        return username;
    }

}
