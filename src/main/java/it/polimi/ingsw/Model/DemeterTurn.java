package it.polimi.ingsw.Model;

public class DemeterTurn extends DefaultTurn {

    Integer lastX;
    Integer lastY;
    boolean oneBuild;


    public DemeterTurn(Player player) {
        super(player);
        lastX = null;
        lastY = null;
    }

    @Override
    public void start(){
        super.start();
        lastX = null;
        lastY = null;
        oneBuild = false;
    }

    @Override
    public void build(Worker worker, int x, int y) throws NullPointerException {
        if(!running){
            throw new RuntimeException("Turn not started!");
        }
        if (!canBuild) {
            throw new RuntimeException("You can't build!");
        }
        try {
            // controllo che quando voglio costruire la seconda volta
            // non costruisco sulla stessa posizione della prima
            if (lastX == x && lastY == y) {
                throw new RuntimeException("Can't build here!");
            }
            buildAction.build(worker, x, y);
            canBuild = false; //non posso più costruire
        } catch (NullPointerException e) { // vuol dire che è la prima volta che costruisco poichè lastX lastY sono null
            buildAction.build(worker, x, y);
            //salvo la posizione della prima costruzione
            lastX = x;
            lastY = y;
            oneBuild = true; //indico che ho costruito almeno una volta
        }
    }

    @Override
    public void end(){
        if(canMove){
            throw new RuntimeException("Can't end turn! You have to move!");
        }else if(!oneBuild){ //controllo di aver costruito almeno una volta
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false;
    }
}
