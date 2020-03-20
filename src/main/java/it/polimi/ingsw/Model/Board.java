package it.polimi.ingsw.Model;

public class Board {
    private Box[][] board=new Box[5][5];

    public Board()
    {
        int x;
        int y;
        for(y=0;y<5;y++)
            for(x=0;x<5;x++) {
                board[x][y]=new Box();
            }
    }

    //TODO: fare metodo getBox(x:int, y:int):Box

    public void placePawn(Worker worker,int x,int y) throws IndexOutOfBoundsException
    {
        board[x][y].setPawn(worker);
        //TODO: controllare che x e y non siano fuori dai limiti della board
    }
}
