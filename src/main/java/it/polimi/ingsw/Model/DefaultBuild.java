package it.polimi.ingsw.Model;

public class DefaultBuild implements Build {

    private Board board;

    public DefaultBuild(Player player){
        this.board = player.getSession().getBoard();
    }

    @Override
    public void build(Worker worker,int x, int y) {
        try {
            Box box = board.getBox(x, y);
            int wX,wY;
            wX = worker.getXPos();
            wY= worker.getXPos();
            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 ) {
                throw new RuntimeException("Impossibile costruire a due celle di distanza");
            }
            if(x == wX && y == wY) {
                throw new RuntimeException("Impossibile costruire sulla stessa cella della pedina");
            }
            if(!board.getBox(x,y).isFree()) {
                throw new RuntimeException("Impossibile costruire sulla stessa cella di un'altra pedina");
            }
            else {
                switch (box.getBlock()) {
                    case TERRAIN:
                        box.build(Block.LONE);
                        break;
                    case LONE:
                        box.build(Block.LTWO);
                        break;
                    case LTWO:
                        box.build(Block.LTHREE);
                        break;
                    case LTHREE:
                        box.build(Block.DOME);
                        break;
                    case DOME:
                        throw new RuntimeException("Can't build here! There is a DOME");
                    default:
                        throw new RuntimeException("Unexpected case!");
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }
    }

    void buildDome(int x, int y){};
}
