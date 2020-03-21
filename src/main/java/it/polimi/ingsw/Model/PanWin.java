package it.polimi.ingsw.Model;

//TODO: ho esteso la classe di default
public class PanWin extends DefaultWin {

    private Board board;
    private Worker worker1;
    private Worker worker2;

    public PanWin(Player player) {
        super(player);
        this.board = player.getSession().getBoard();
        this.worker1 = player.getWorker1();
        this.worker2 = player.getWorker2();
    }

    @Override
    public boolean winChecker(){
        if(super.winChecker()){
            return true;
        }else {
            Box w1LastBox = worker1.getLastBox();
            Box w1ActualBox = board.getBox(worker1.getXPos(), worker1.getYPos());
            Box w2LastBox = worker2.getLastBox();
            Box w2ActualBox = board.getBox(worker2.getXPos(), worker2.getYPos());

            return w1LastBox.getDifference(w1ActualBox) == 2 || w2LastBox.getDifference(w2ActualBox) == 2;
        }
    }
}
