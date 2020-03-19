package it.polimi.ingsw.Model;

public class Worker {
    private int id;
    //Memorizzo la posizone della pedina in modo
    //da recuperarla più velocemente all'occorrenza
    private int x;
    private int y;

    public Worker(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getXPos(){
        return this.x;
    }

    public int getYPos(){
        return this.y;
    }
}
