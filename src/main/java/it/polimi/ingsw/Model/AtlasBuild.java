package it.polimi.ingsw.Model;

//TODO: ho esteso la classe di Default (discutere)
public class AtlasBuild extends DefaultBuild {

    private Board board;

    public AtlasBuild(Player player) {
        super(player);
        player.getSession().getBoard();
    }

    public void buildDome(int x, int y){
        Box box = board.getBox(x, y);

        try {
            switch (box.getBlock()){
                case TERRAIN:
                    box.setTypeOfBuilding(Block.DOME);
                    break;
                case LONE:
                    box.setTypeOfBuilding(Block.DOME);
                    break;
                case LTWO:
                    box.setTypeOfBuilding(Block.DOME);
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
