package it.polimi.ingsw;


import it.polimi.ingsw.Client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ClientGUIApp extends Application {

    Client client = new Client("127.0.0.1", 12346);
    public static Stage window = new Stage();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            client.startClient();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartMenu.fxml")));
            Scene scene = new Scene(root, 715, 776);
            window.setScene(scene);
            //window.setFullScreen(true);
            //window.setMaximized(true);
            //window.setResizable(false);
            window.setTitle("Santorini");
            window.show();
            window.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
