package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Exceptions.AlreadyExistingSessionException;
import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Messages.InvalidChoiceMessage;
import it.polimi.ingsw.Messages.SessionListMessage;
import it.polimi.ingsw.Messages.SuccessfulCreate;
import it.polimi.ingsw.Messages.SuccessfulJoin;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

public class MainController extends Observable<Object> implements Observer<Object> {

    StartMenuController startController;
    JoinMenuController joinController;
    CreateMenuController createController;
    PlayingStageController playingStageController;
    WaitController waitController;

    Client client;

    private boolean playing;

    public MainController(){
        startController = new StartMenuController();
        joinController = new JoinMenuController();
        createController = new CreateMenuController();
        playingStageController = new PlayingStageController();
        waitController = new WaitController();
        playing = false;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPlaying(boolean playing){this.playing=playing; }

    public boolean isPlaying(){return playing;}

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
                if((int)msg == 0){
                    waitController.handleStart();
                }
                else if((int)msg == 2 ){
                    try {
                        playingStageController.setActionLabel(client.getStatus().getActions());
                    }catch(NullPointerException e){}
                }else if((int)msg == 1){
                    playingStageController.updateBoard();
                }
            } else if (msg instanceof InvalidChoiceMessage){
                playingStageController.handleException((InvalidChoiceMessage)msg);
            }
    }



}
