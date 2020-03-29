package it.polimi.ingsw.Model;
import it.polimi.ingsw.Exceptions.PlayerLostException;
public class DefaultTurn implements Turn {

    Move moveAction;
    Build buildAction;
    Win winAction;
    boolean running;
    static boolean canGoUp = true;
    boolean canMove;
    boolean canBuild;
    boolean win;
    Board board;
    Worker worker1;
    Worker worker2;

    public DefaultTurn(Player player) {
        this.running = false;
        this.win = false;
        this.board = player.getSession().getBoard();
        this.worker1 = player.getWorker1();
        this.worker2 = player.getWorker2();
        this.moveAction = player.getMoveAction();
        this.buildAction = player.getBuildAction();
        this.winAction = player.getWinAction();
    }

    @Override
    public void start() {
        if (running) { // controlla che il turno non è stato già iniziato
            throw new RuntimeException("Already start!");
        }
        if (canGoUp) {
            if (!worker1.canMove(true) && !worker2.canMove(true)) { //controlla che il giocatore ha almeno una possibilità di muoversi
                throw new PlayerLostException("Your workers cannot make any moves!");
            }
        }else {
            if (!worker1.canMove(false) && !worker2.canMove(false)) { //controlla che il giocatore ha almeno una possibilità di muoversi
                throw new PlayerLostException("The Player has lost");
            }
        }
        running = true;
        canMove = true; // abilita la mossa
        canBuild = false;
    }

    @Override
    public void move(Worker worker, int x, int y) {
        if (!running) {
            throw new RuntimeException("Turn not started!");
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
        win = winAction.winChecker();
    }

    @Override
    public void build(Worker worker, int x, int y) {
        if (!running) {
            throw new RuntimeException("Turn not started!");
        }
        if (!canBuild) {
            throw new RuntimeException("You can't build!");
        }
        buildAction.build(worker, x, y);
        canBuild = false;
    }

    @Override
    public boolean won() {
        return win;
    }

    @Override
    public void end() {
        //Controllo che tutte le azioni che dovevano essere fatte, sono state fatte
        if (canMove) {
            throw new RuntimeException("Can't end turn! You have to move!");
        } else if (canBuild) {
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false; // finisco il turno
    }
}
