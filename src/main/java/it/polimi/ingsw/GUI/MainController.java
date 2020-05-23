package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Exceptions.AlreadyExistingSessionException;
import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Messages.SessionListMessage;
import it.polimi.ingsw.Messages.SuccessfulCreate;
import it.polimi.ingsw.Messages.SuccessfulJoin;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MainController extends Observable<Object> implements Observer<Object> {

    FXMLLoader loaderStart;
    FXMLLoader loaderCreate;
    FXMLLoader loaderJoin;
    FXMLLoader loaderPlaying;

    Parent startRoot;
    Parent joinRoot;
    Parent createRoot;
    Parent playingRoot;

    StartMenuController startController;
    JoinMenuController joinController;
    CreateMenuController createController;
    PlayingStageController playingStageController;

    Client client;

    public MainController(){

        startController = new StartMenuController();
        joinController = new JoinMenuController();
        createController = new CreateMenuController();
        playingStageController = new PlayingStageController();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void update(Object msg) {
            if (msg instanceof SessionListMessage) {
                JoinMenuController.display((SessionListMessage) msg);
            } else if (msg instanceof AlreadyExistingSessionException) {
                CreateMenuController.handleException((Exception) msg);
            } else if (msg instanceof SuccessfulCreate) {
                createController.handleStart();
            } else if (msg instanceof SuccessfulJoin) {
                joinController.handleStart();
            } else if (msg instanceof InvalidUsernameException) {
                joinController.handleException((Exception)msg);
            } else if (msg instanceof Integer){
                if((int)msg == 2){
                    try {
                        PlayingStageController.setActionLabel(client.getStatus().getActions());
                    }catch(NullPointerException e){}
                }
            }
    }



}
