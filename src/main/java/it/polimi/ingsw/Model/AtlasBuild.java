package it.polimi.ingsw.Model;

//TODO: ho esteso la classe di Default (discutere)
public class AtlasBuild extends DefaultBuild {

    private Board board;

    public AtlasBuild(Player player) {
        super(player);
        board = player.getSession().getBoard();
    }

    public void buildDome(int x, int y){
        Box box = board.getBox(x, y);

        try {
            switch (box.getBlock()){
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
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }
    }
}
