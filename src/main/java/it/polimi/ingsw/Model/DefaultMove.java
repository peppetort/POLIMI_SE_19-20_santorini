package it.polimi.ingsw.Model;

public class DefaultMove implements Move {

    private Board board;


    public DefaultMove(Player player){
        this.board = player.getSession().getBoard();
    }

    @Override
    public void move(Worker worker, int x, int y) {
        try {
            int wX = worker.getXPos();
            int wY = worker.getYPos();

            Box workerBox = board.getBox(wX, wY);
            Box nextBox = board.getBox(x, y);

            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY)){
                throw new RuntimeException("Invalid move!");
            }else if(!nextBox.isFree()){
                throw new RuntimeException("Box already occupied!");
            }else if(!workerBox.compare(nextBox)){
                throw new RuntimeException("Level not compatible!");
            }else{
                board.placePawn(worker, x, y);
                workerBox.removePawn();  //rimuovo pedina dalla vecchia pos
                worker.updateLastBox(workerBox); // aggiorno l'ultima box nel worker
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }catch (NullPointerException e){
            System.out.println("Pawns not in board!");
        }

    }
}
