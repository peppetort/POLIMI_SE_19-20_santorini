package it.polimi.ingsw.GUI;

import it.polimi.ingsw.ClientGUIApp;
import it.polimi.ingsw.Exceptions.AlreadyExistingSessionException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PlayerCreateSessionMessage;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateMenuController implements Initializable {

    public static MainController mainController;

    public Button createButton;
    public TextField sessionName;
    public TextField username;
    public ComboBox cardBox;
    public ComboBox playerBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardBox.getItems().addAll("Yes","No");
        cardBox.getSelectionModel().selectFirst();
        playerBox.getItems().addAll(2,3);
        playerBox.getSelectionModel().selectFirst();

    }

    public static void setMainController(MainController mc){
        mainController = mc;
    }

    public void handleBack() throws IOException{
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("StartMenu.fxml"));
        Scene scene = new Scene(pane, 715.0, 776.0);
            ClientGUIApp.window.setScene(scene);
    }

    public void handleCreate(){
        boolean simple;

        if(cardBox.getSelectionModel().getSelectedItem().equals("Yes")){
            simple = false;
        }else{
            simple = true;
        }
        Message msg = new PlayerCreateSessionMessage(username.getText(),sessionName.getText(),(int)playerBox.getSelectionModel().getSelectedItem(),simple);
        mainController.notify(msg);
    }

    public static void handleException(Exception msg){

        if(msg instanceof AlreadyExistingSessionException){

            //TODO : SEPARARE IN UNA CLASSE :)
            Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Look, an Error Dialog");
                    alert.setContentText("Ooops, there was an error!");
                    alert.showAndWait();
                });
        }
    }

    public void handleStart(){
            Platform.runLater(() ->{
                    try {
                        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("PlayingStage.fxml"));
                        Scene scene = new Scene(pane, 1280, 720);
                        ClientGUIApp.window.setScene(scene);
                    }catch (IOException e){}
                });
    }

}
