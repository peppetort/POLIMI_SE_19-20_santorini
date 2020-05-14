package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

import java.util.HashMap;

public class DefaultTurn implements Turn {

    static boolean canGoUp = true;
    final Move moveAction;
    final Build buildAction;
    final Win winAction;
    final Board board;
    boolean running;
    boolean canMove;
    boolean canBuild;
    boolean win;
    Worker worker;
    Worker otherWorker;
    Player player;
    HashMap<Actions, Boolean> playerMenu;

    public DefaultTurn(Player player) {
        this.player = player;
        this.running = false;
        this.win = false;
        this.board = player.getSession().getBoard();
        this.moveAction = player.getMoveAction();
        this.buildAction = player.getBuildAction();
        this.winAction = player.getWinAction();
        this.playerMenu = player.getPlayerMenu();
    }

    @Override
    public void start(Worker worker) {

        if (player.getWorker1().equals(worker)) {
            otherWorker = player.getWorker2();
        } else {
            otherWorker = player.getWorker1();
        }

        if (!playerMenu.get(Actions.SELECT)) {
            throw new RuntimeException("Can't start!");
        }
        if (running) { // controlla che il turno non è stato già iniziato
            throw new RuntimeException("Already start!");
        }
        boolean canMoveSelected = worker.canMove(canGoUp);
        boolean canMoveOther = otherWorker.canMove(canGoUp);

        if(!canMoveSelected && !canMoveOther){
            throw new PlayerLostException("Your workers cannot make any moves!");
        }else if(!canMoveSelected){
            throw new RuntimeException("Selected worker cannot make any moves");
        }else {
            board.removeAction();
            running = true;
            this.worker = worker;
            canMove = true; // abilita la mossa
            canBuild = false;
            playerMenu.replace(Actions.SELECT, false);
            playerMenu.replace(Actions.MOVE, true);
            playerMenu.replace(Actions.UNDO, true);
            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.MOVE);
            message.addAction(Actions.UNDO);
            player.notify(message);
        }

    }

    @Override
    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, AthenaGoUpException, InvalidMoveException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canMove) {
            throw new RuntimeException("You can't move!");
        }
        if (!canGoUp) { // se è true un giocatore avversario ha usato ATHENA
            moveAction.moveNoGoUp(worker, x, y);
        } else {
            moveAction.move(worker, x, y);
        }
        canMove = false;
        canBuild = true; // abilita la costruzione
        playerMenu.replace(Actions.MOVE, false);
        playerMenu.replace(Actions.BUILD, true);

        win = winAction.winChecker();

        if(!win) {
            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.BUILD);
            message.addAction(Actions.UNDO);
            player.notify(message);
        }

    }

    @Override
    public void build(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canBuild) {
            throw new RuntimeException("You can't build!");
        }
        buildAction.build(worker, x, y);
        canBuild = false;
        playerMenu.replace(Actions.BUILD, false);
        playerMenu.replace(Actions.END, true);

        ActionsUpdateMessage message = new ActionsUpdateMessage();
        message.addAction(Actions.END);
        message.addAction(Actions.UNDO);
        player.notify(message);

    }

    @Override
    public void buildDome(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canBuild) {
            throw new RuntimeException("You can't build!");
        }
        //throw new RuntimeException("You can't build a dome!");
        ((AtlasBuild) buildAction).buildDome(worker, x, y);
        canBuild = false;
        playerMenu.replace(Actions.BUILD, false);
        playerMenu.replace(Actions.END, true);

        ActionsUpdateMessage message = new ActionsUpdateMessage();
        message.addAction(Actions.END);
        message.addAction(Actions.UNDO);
        player.notify(message);
    }


    @Override
    public boolean won() {
        return win;
    }

    @Override
    public void end() {
        //Controllo che tutte le azioni che dovevano essere fatte, sono state fatte
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        } else if (canMove) {
            throw new RuntimeException("Can't end turn! You have to move!");
        } else if (canBuild) {
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false; // finisco il turno
        playerMenu.replace(Actions.END, false);
    }

    //azione che ristabilisce la condizione all'inizio del turno della board
    //inizializzazione del turno a start, il turno ricomincia
    public void undo() {
        board.restore();
        playerMenu.replace(Actions.DECK, false);
        playerMenu.replace(Actions.CARD, false);
        playerMenu.replace(Actions.PLACE, false);
        playerMenu.replace(Actions.SELECT, true);
        playerMenu.replace(Actions.MOVE, false);
        playerMenu.replace(Actions.BUILD, false);
        playerMenu.replace(Actions.END, false);
        this.running = false;
        this.win = false;

        try {
            if (player.getCard().equals(God.ATHENA)) {
                canGoUp = true;
            }
        }catch (SimpleGameException ignored){}

        ActionsUpdateMessage message = new ActionsUpdateMessage();
        message.addAction(Actions.SELECT);
        player.notify(message);
    }
    //TODO: gestione undo per le classi che ereditano da questa (solo alcune che non sono ancora state testate)
}
