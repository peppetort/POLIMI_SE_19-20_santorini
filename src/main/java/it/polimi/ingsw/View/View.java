package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

/**
 * {@link View} is the abstract view for the MVC pattern. It's an {@link Observable} for the {@link it.polimi.ingsw.Controller.Controller}.
 *
 * Messages are read in the {@link it.polimi.ingsw.Server.SocketClientConnection} that notifies the message receiver
 * object in the {@link RemoteView} that trigger each method of the {@link View}.
 *
 * Each {@link Message} has {@link Player} equals to null because the {@link it.polimi.ingsw.Client.Client} doesn't know
 * his reference, so the {@link View} set the {@link Player} field (each {@link View} has a single {@link Player} as attribute).
 */
public abstract class View extends Observable<Message> implements Observer<Message>{

    Player player;

    /**
     * Constructor that sets the {@link Player}: player field.
     * @param player
     */
    protected View(Player player) {
        this.player = player;
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerDeckMessage}.
     * @param message
     */
    public void handleDeck(Message message) {
        ((PlayerDeckMessage)message).setPlayer(player);
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerCardChoiceMessage}.
     * @param message
     */
    public void handleCardChoice(Message message) {
        ((PlayerCardChoiceMessage)message).setPlayer(player);
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and the reference of the player's {@link it.polimi.ingsw.Model.Worker},
     * then it notifies the {@link it.polimi.ingsw.Controller.Controller} a {@link PlayerSelectMessage}.
     * @param message
     */
    public void handleSelect(Message message){
        ((PlayerSelectMessage)message).setPlayer(player);
        switch(((PlayerSelectMessage)message).getNum()){
            case 1:
                ((PlayerSelectMessage)message).setWorker(player.getWorker1());
                break;
            case 2:
                ((PlayerSelectMessage)message).setWorker(player.getWorker2());
                break;
        }
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerMoveMessage}.
     * @param message
     */
    public void handleMove(Message message) {
        ((PlayerMoveMessage)message).setPlayer(player);
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerBuildMessage}.
     * @param message
     */
    public void handleBuild(Message message) {
        ((PlayerBuildMessage)message).setPlayer(player);
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerBuildDomeMessage}.
     * @param message
     */
    public void handleBuildDome(Message message) {
        ((PlayerBuildDomeMessage)message).setPlayer(player);
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerPlacePawnsMessage}.
     * @param message
     */
    public void handlePlacing(Message message) {
        ((PlayerPlacePawnsMessage)message).setPlayer(player);
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerUndoMessage}.
     * @param message
     */
    public void handleUndo(Message message){
        ((PlayerUndoMessage)message).setPlayer(player);
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerEndMessage}.
     * @param message
     */
    public void handleEnd(Message message) {
        ((PlayerEndMessage)message).setPlayer(player);
        notify(message);
    }

    /**
     * It sets the {@link Player} field with the 'player' field and notifies the {@link it.polimi.ingsw.Controller.Controller}
     * a {@link PlayerChatMessage}.
     * @param message
     */
    public void handleChat(Message message) {
        ((PlayerChatMessage)message).setPlayer(player);
        notify(message);
    }

}