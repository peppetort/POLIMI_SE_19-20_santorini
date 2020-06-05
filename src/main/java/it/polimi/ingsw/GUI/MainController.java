package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Client.Status;
import it.polimi.ingsw.Exceptions.AlreadyExistingSessionException;
import it.polimi.ingsw.Exceptions.InvalidUsernameException;
import it.polimi.ingsw.Exceptions.SessionNotExistsException;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import javafx.application.Platform;

import java.io.IOException;

public class MainController extends Observable<Object> implements Observer<Object> {

	StartMenuController startController;
	JoinMenuController joinController;
	CreateMenuController createController;
	PlayingStageController playingStageController;
	WaitController waitController;

	Client client;

	private boolean playing;

	public MainController() {
		startController = new StartMenuController();
		joinController = new JoinMenuController();
		createController = new CreateMenuController();
		playingStageController = new PlayingStageController();
		waitController = new WaitController();
		playing = false;
	}

	public void setCard(God card) {
		PlayingStageController.handleCardChoice(card);
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isPlaying() {
		return playing;
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
            } else if (msg instanceof InvalidUsernameException || msg instanceof SessionNotExistsException) {
                joinController.handleException((Exception) msg);
            } else if (msg instanceof ChatUpdateMessage) {
                PlayingStageController.handleChatUpdate((ChatUpdateMessage)msg);
            } else if (msg instanceof Integer){
                if((int)msg == 0){
                    waitController.handleStart();
                }
                else if((int)msg == 2 ){
                    try {
                    	String username = client.getStatus().getTurn();
                    	Color player = client.getStatus().getTurnColor();
                    	God god = client.getStatus().getTurnGod();

                    	PlayingStageController.setTurnPlayer(username, player, god);
                        PlayingStageController.setActionLabel(client.getStatus().getActions());
                    }catch(NullPointerException ignored){}
                }else if((int)msg == 1){
                    PlayingStageController.updateBoard();
                }
            } else if (msg instanceof InvalidChoiceMessage){
                PlayingStageController.handleException((InvalidChoiceMessage)msg);
            }else if (msg instanceof Status){
                if(msg.equals(Status.WON)){
                    Platform.runLater(() ->{
                        try{
                            EndController dialog = new EndController();
                            dialog.display(true);
                        }catch (IOException ignored){}
                    });
                } else{
                    Platform.runLater(() ->{
                        try{
                            EndController dialog = new EndController();
                            dialog.display(false);
                        }catch (IOException ignored){}
                    });
                }
            }
    }
}
