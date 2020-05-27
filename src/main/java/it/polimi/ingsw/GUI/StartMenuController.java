package it.polimi.ingsw.GUI;

import it.polimi.ingsw.ClientGUIApp;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartMenuController implements Initializable {

    public Button joinButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleCreate() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("CreateMenu.fxml"));
            Scene scene = new Scene(pane, 715, 776);
            ClientGUIApp.window.setScene(scene);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void handleJoin() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("JoinMenu.fxml"));
            Scene scene = new Scene(pane, 715, 776);
            ClientGUIApp.window.setScene(scene);
        }catch (IOException e){}
    }

}