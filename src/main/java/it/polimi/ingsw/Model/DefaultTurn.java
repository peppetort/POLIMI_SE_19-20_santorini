package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Observer.Observable;

import java.util.HashMap;

/**
 * Represents Player's turn in case the game is without cards
 */
public class DefaultTurn implements Turn  {

    /**
     * Reference to the {@link Move} type object owned by the {@link Player}
     */
    final Move moveAction;

    /**
     * Reference to the {@link Build} type object owned by the {@link Player}
     */
    final Build buildAction;

    /**
     * Reference to the {@link Win} type object owned by the {@link Player}
     */
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

    /**
     * Constructor of the class {@link DefaultTurn}
     *
     * @param player
     */
    public DefaultTurn(Player player)  {
        this.player = player;
        this.running = false;
        this.win = false;
        this.board = player.getSession().getBoard();
        this.moveAction = player.getMoveAction();
        this.buildAction = player.getBuildAction();
        this.winAction = player.getWinAction();
        this.playerMenu = player.getPlayerMenu();
    }

    /**
     * Start the turn by setting the {@link Worker} you want to play with.
     * It's the first method to be invoked to perform any other action within the turn.
     *
     * @param worker you want to play with
     */
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
        boolean canGoUp = player.getSession().getCanGoUp();

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

            player.getSession().notify(message);

        }

    }

    /**
     * Move the chosen {@link Worker} into the specified coordinates.
     * It calls {@link Move#move(Worker, int x, int y)} or {@link Move#moveNoGoUp(Worker, int x, int y)} using
     * {@link DefaultTurn#moveAction}
     *
     * @param x coordinate for the board
     * @param y coordinate for the board
     * @throws IndexOutOfBoundsException if chosen coordinates go outside the board limits
     * @throws NullPointerException if you try to move a worker that has null reference
     * @throws CantGoUpException if you try to level up but you can't
     * @throws InvalidMoveException if chosen coordinates are already occupied by another player
     */
    @Override
    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, CantGoUpException, InvalidMoveException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canMove) {
            throw new RuntimeException("You can't move!");
        }
        if (!player.getSession().getCanGoUp()) { // se è true un giocatore avversario ha usato ATHENA
            try {
                moveAction.moveNoGoUp(worker, x, y);
            }catch (CantGoUpException e){
                throw new CantGoUpException(e.getMessage() + " Opponent used Athena's power");
            }
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

            if(!player.getSession().isSimple() &&  player.getCard().equals(God.ATLAS)){
                message.addAction(Actions.DOME);
            }

            message.addAction(Actions.UNDO);

            player.getSession().notify(message);
        }

    }

    /**
     * Build into the specified coordinates.
     * It calls {@link Build#build(Worker, int x, int y)} using
     * {@link DefaultTurn#buildAction}
     *
     * @param x coordinate for the board
     * @param y coordinate for the board
     * @throws IndexOutOfBoundsException if chosen coordinates go outside the board limits
     * @throws NullPointerException if you try to move a worker that has null reference
     * @throws InvalidBuildException if you try to build too far from {@link Worker} position or into a box containing a DOME
     */
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

        player.getSession().notify(message);

    }

    /**
     * Builds a dome into the specified coordinates.
     * It calls {@link AtlasBuild#buildDome(Worker, int x, int y)} using
     * {@link DefaultTurn#buildAction}
     *
     * @param x coordinate for the board
     * @param y coordinate for the board
     * @throws IndexOutOfBoundsException if chosen coordinates go outside the board limits
     * @throws NullPointerException if you try to move a worker that has null reference
     * @throws InvalidBuildException if you try to build too far from {@link Worker} position or into a box containing a DOME
     */
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
        player.getSession().notify(message);
    }


    /**
     * @return true if the player won otherwise false
     */
    @Override
    public boolean won() {
        return win;
    }

    /**
     * Ends turn
     */
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

    /**
     *Restore conditions at the beginning of the turn calling {@link Board#restore()}
     */
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
                player.getSession().updateCanGoUp(true);
            }
        }catch (SimpleGameException ignored){}

        ActionsUpdateMessage message = new ActionsUpdateMessage();
        message.addAction(Actions.SELECT);
        player.getSession().notify(message);
    }
    //TODO: gestione undo per le classi che ereditano da questa (solo alcune che non sono ancora state testate)
}
