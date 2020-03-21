package it.polimi.ingsw.Model;

public class Worker {
    private String id;
    //Memorizzo la posizone della pedina in modo
    //da recuperarla più velocemente all'occorrenza
    private Integer x;
    private Integer y;
    private Box lastBox;

    public Worker(String id){
        this.id = id;
        this.x = null;
        this.y = null;
        this.lastBox = null;
    }

    public String getId(){
        return this.id;
    }

    public void setPos(int x, int y){
        if(x>=0 && x<5 && y>=0 && y<5) {
            this.x = x;
            this.y = y;
        }else {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
    }

    public int getXPos(){
        if(this.x == null){
            throw new NullPointerException("Position not Initialized!");
        }else {
            return this.x;
        }
    }

    public int getYPos(){
        if(this.y == null){
            throw new NullPointerException("Position not Initialized!");
        }else {
            return this.y;
        }
    }

    public Box getLastBox(){
        return this.lastBox;
    }

    public void updateLastBox(Box box){
        lastBox = new Box();
        lastBox.setPawn(box.getPawn());
        lastBox.build(box.getBlock());
    }
}
