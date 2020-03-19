package it.polimi.ingsw.Model;

public class DefaultBuild implements Build {

    private Board board;

    public DefaultBuild(Player player){
        this.board = player.getSession().getBoard();
    }

    @Override
    public void build(int x, int y) {
        Box box = board.getBox(x, y);

        try {
                switch (box.getBlock()){
                    case TERRAIN:
                        box.setTypeOfBuilding(Block.LONE);
                        break;
                    case LONE:
                        box.setTypeOfBuilding(Block.LTWO);
                        break;
                    case LTWO:
                        box.setTypeOfBuilding(Block.LTHREE);
                        break;
                    case LTHREE:
                        box.setTypeOfBuilding(Block.DOME);
                        break;
                    case DOME:
                        throw new RuntimeException("Can't build here! There is a DOME");
                        break;
                    default:
                        throw new RuntimeException("Unexpected case!");
                }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }
    }
}
