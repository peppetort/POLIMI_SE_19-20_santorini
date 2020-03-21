package it.polimi.ingsw.Model;

/*
    worker può spostarsi nella casella di un lavoratore avversario
    e costringendolo a occupare la casella appena liberata scambiando le posizioni
 */

public class ApolloMove implements Move {

    private Board board;
    private Player player;


    public ApolloMove(Player player){
        this.player = player;
        this.board = player.getSession().getBoard();
    }

    @Override
    public void move(Worker worker, int x, int y) {
        int wX = worker.getXPos();
        int wY = worker.getYPos();

        Box workerBox = board.getBox(wX, wY);
        Box nextBox = board.getBox(x, y);

        try {
            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY)){
                throw new RuntimeException("Invalid move!");
            }else if(!workerBox.compare(nextBox)){
                throw new RuntimeException("Level not compatible!");
            }else{
                if(!nextBox.isFree()){
                    Worker other = nextBox.getPawn();
                    // Controllo che l'altra pedina è una pedina avversaria
                    if(other.getId() != player.getWorker1().getId() && other.getId() != player.getWorker2().getId()){
                        board.placePawn(other, wX, wY); //posiziono la pedina avversaria nella mia posizione
                        other.setPos(wX, wY); //aggiorno coordinate pedina avversaria
                        board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                        worker.setPos(x, y); //aggiorno coordinate pedona
                    }else {
                        throw new RuntimeException("Can't place pawn here!");
                    }
                }else {
                    board.placePawn(worker, x, y);
                    workerBox.removePawn(); //rimuovo pedina dalla vecchia pos
                    worker.setPos(x, y);   //aggiorno cordinate pedina
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }
    }
}
