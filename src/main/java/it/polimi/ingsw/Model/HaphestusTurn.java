package it.polimi.ingsw.Model;

public class HaphestusTurn extends DefaultTurn {

    Integer lastX;
    Integer lastY;
    boolean oneBuild;

    public HaphestusTurn(Player player) {
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
            //controllo che la seconda volta che costruisco, costruisco sulla stessa
            //box della prima
            if (lastX != x && lastY != y) {
                throw new RuntimeException("Can't build here!");
            }
            buildAction.build(worker, x, y);
            canBuild = false;
        } catch (NullPointerException e) {
            buildAction.build(worker, x, y);
            oneBuild = true;

            //se la prima volta che costruisco, costruisco un livello
            //tre, allora non posso usare il potere della carta => disabilito la costruzione
            if(board.getBox(x, y).getBlock() == Block.LTHREE){
                canBuild = false;
            }else {
                lastX = x;
                lastY = y;
            }
        }
    }

    @Override
    public void end(){
        if(canMove){
            throw new RuntimeException("Can't end turn! You have to move!");
        }else if(!oneBuild){
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false;
    }
}
