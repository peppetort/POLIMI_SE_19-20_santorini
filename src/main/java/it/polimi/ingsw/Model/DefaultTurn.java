package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.BoardUpdate;
import it.polimi.ingsw.Messages.MenuMessage;

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
    HashMap<String, Boolean> playerMenu;

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

        if(player.getWorker1().equals(worker)){
            otherWorker = player.getWorker2();
        }else {
            otherWorker = player.getWorker1();
        }

        if(!playerMenu.get("start")){
            throw new RuntimeException("Can't start!");
        }
        if (running) { // controlla che il turno non è stato già iniziato
            throw new RuntimeException("Already start!");
        }
        if (!worker.canMove(canGoUp) && !otherWorker.canMove(canGoUp)) { //controlla che il giocatore ha almeno una possibilità di muoversi
            throw new PlayerLostException("Your workers cannot make any moves!");
        }
        running = true;
        this.worker = worker;
        canMove = true; // abilita la mossa
        canBuild = false;
        playerMenu.replace("start", false);
        playerMenu.replace("move", true);

        player.notify(new MenuMessage(playerMenu));
        player.notify(new BoardUpdate(board.data(),player.getSession().getPlayers()));

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
        playerMenu.replace("move", false);
        playerMenu.replace("build", true);
        win = winAction.winChecker();

        player.notify(new MenuMessage(playerMenu));
        player.notify(new BoardUpdate(board.data(),player.getSession().getPlayers()));
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
        playerMenu.replace("build", false);
        playerMenu.replace("end", true);

        player.notify(new MenuMessage(playerMenu));
        player.notify(new BoardUpdate(board.data(),player.getSession().getPlayers()));

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
        playerMenu.replace("end", false);
    }

}
