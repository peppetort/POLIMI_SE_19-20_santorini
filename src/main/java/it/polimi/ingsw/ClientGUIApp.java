package it.polimi.ingsw;


import it.polimi.ingsw.Client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StartMenu.fxml"));
            Scene scene = new Scene(root, 715, 776);
            window.setScene(scene);
            window.setWidth(715);
            window.setHeight(776);
            window.setResizable(false);
            //window.setFullScreen(true);
            //window.setMaximized(true);
            window.setTitle("Santorini");
            window.show();

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
