package it.polimi.ingsw.Model;

public class DefaultWin implements Win{

    private Board board;
    private Worker worker1;
    private Worker worker2;

    public DefaultWin(Player player){
        this.board = player.getSession().getBoard();
        this.worker1 = player.getWorker1();
        this.worker2 = player.getWorker2();
    }

    @Override
    public boolean winChecker() {
        int w1X = worker1.getXPos();
        int w1Y = worker1.getYPos();
        int w2X = worker2.getXPos();
        int w2Y = worker2.getYPos();

        return board.getBox(w1X, w1Y).getBlock() == Block.LTHREE || board.getBox(w2X, w2Y).getBlock() == Block.LTHREE;
    }
}
