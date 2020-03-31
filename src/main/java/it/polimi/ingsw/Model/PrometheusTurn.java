package it.polimi.ingsw.Model;

public class PrometheusTurn extends DefaultTurn {

    boolean startBuild;

    public PrometheusTurn(Player player) {
        super(player);
    }

    @Override
    public void start(Worker worker){
        super.start(worker);
        if(!worker.moveGoUp()){
            canMove = true;
            canBuild = true;
        }
        startBuild = false;
    }

    @Override
    public void move(int x, int y){
        super.move( x, y);
        startBuild = true;
    }


    @Override
    public void build(int x, int y){
        super.build( x, y);
        if(!startBuild){
            startBuild = true;
        }
    }
}
