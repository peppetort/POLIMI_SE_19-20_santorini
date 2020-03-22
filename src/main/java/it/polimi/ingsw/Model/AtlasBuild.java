package it.polimi.ingsw.Model;

public class AtlasBuild extends DefaultBuild{

    private Board board;

    public AtlasBuild(Player player) {
        super(player);
        board = player.getSession().getBoard();
    }

    public void buildDome(Worker worker, int x, int y){
        try {
            int wX = worker.getXPos();
            int wY = worker.getYPos();

            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY)) {
                throw new RuntimeException("Invalid build!");
            }else {

                Box box = board.getBox(x, y);

                switch (box.getBlock()) {
                    case TERRAIN:
                    case LONE:
                    case LTWO:
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
        }catch (NullPointerException e){
            System.out.println("Worker not in board");
        }
    }
}
