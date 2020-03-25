package it.polimi.ingsw.Model;

public class PrometheusTurn extends DefaultTurn {

    boolean startBuild;

    public PrometheusTurn(Player player) {
        super(player);
    }

    @Override
    public void start(){
        if(running){
            throw new RuntimeException("Already start!");
        }
        running = true;
        canMove = true;
        startBuild = false;
        canBuild = true;
        //TODO: bisogna controllare se il giocatore può salire di livello o no
    }


    @Override
    public void build(Worker worker, int x, int y){
        super.build(worker, x, y);
        if(!startBuild){
            startBuild = true;
        }
    }
}
