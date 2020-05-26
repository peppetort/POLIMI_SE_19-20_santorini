package it.polimi.ingsw.GUI;

import it.polimi.ingsw.ClientGUIApp;
import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PlayerRetrieveSessions;
import it.polimi.ingsw.Messages.PlayerSelectSession;
import it.polimi.ingsw.Messages.SessionListMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class JoinMenuController implements Initializable {

    private static MainController mainController;

    private static String session;

    public AnchorPane anchorTable;

    public TableView<SessionObject> sessionsTable;
    public TableColumn<SessionObject,String> name;
    public TableColumn<SessionObject,Integer> players;
    public TableColumn<SessionObject,Boolean> cards;

    public static ObservableList<SessionObject> list = FXCollections.observableArrayList();

    public static void setMainController(MainController mc){
        mainController = mc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Message msg = new PlayerRetrieveSessions();
        mainController.notify(msg);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        players.setCellValueFactory(new PropertyValueFactory<>("players"));
        cards.setCellValueFactory(new PropertyValueFactory<>("cards"));

        name.setStyle( "-fx-alignment: CENTER;");
        players.setStyle( "-fx-alignment: CENTER;");
        cards.setStyle( "-fx-alignment: CENTER;");

        list.addListener((ListChangeListener<SessionObject>) change -> {
            while(change.next()) {
                sessionsTable.getItems().add(change.getAddedSubList().get(0));
            }
        });
    }

    public void handleBack() throws IOException{
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("StartMenu.fxml"));
        Scene scene = new Scene(pane, 715, 776);
            ClientGUIApp.window.setScene(scene);
    }

    public void handleJoin() {
        this.session = sessionsTable.getSelectionModel().getSelectedItem().name;
        String username = UsernameDialog.display("Insert your username");
        if(username != null && username.length() >= 1) {
            mainController.notify(new PlayerSelectSession(session, username));
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

    public void handleException(Exception e){
        if(e instanceof InvalidUsernameException){
            System.out.println("Invalid username");
//            SessionObject obj;
//            obj = sessionsTable.getSelectionModel().getSelectedItem();
            Platform.runLater(() -> {
                String username = UsernameDialog.display("Username invalid, please re-insert the username");
                mainController.notify(new PlayerSelectSession(session,username));
            });
        }
    }


    public static void display(SessionListMessage msg){
        HashMap<String,Integer> players = msg.getParticipants();
        HashMap<String,Boolean> cards = msg.getCards();

        for(String s : players.keySet()){
            System.out.println("Session added to list");
            list.add(new SessionObject(s,players.get(s),cards.get(s)));
        }
    }

}
