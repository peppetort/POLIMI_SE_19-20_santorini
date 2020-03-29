package it.polimi.ingsw.Model;

public class PrometheusTurn extends DefaultTurn {

    boolean startBuild;

    public PrometheusTurn(Player player) {
        super(player);
    }

    @Override
    public void start(){
        super.start();
        if(!worker1.moveGoUp() && !worker2.moveGoUp()){
            canMove = true;
            canBuild = true;
        }
        startBuild = false;
    }

    @Override
    public void move(Worker worker, int x, int y){
        super.move(worker, x, y);
        startBuild = true;
    }


    @Override
    public void build(Worker worker, int x, int y){
        super.build(worker, x, y);
        if(!startBuild){
            startBuild = true;
        }
    }
}
