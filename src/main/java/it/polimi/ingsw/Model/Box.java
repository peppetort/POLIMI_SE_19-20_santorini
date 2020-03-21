package it.polimi.ingsw.Model;

public class Box {

    private Block typeOfBuilding;
    private Worker pawn;                //pedina

    public Box()
    {
        typeOfBuilding=Block.TERRAIN;
        pawn=null;
    }


    public Worker getPawn(){return pawn;}

    public void setPawn(Worker pawn){
        this.pawn=pawn;
    }

    //TODO: fare metodo removePawn():void   FATTO
    public void removePawn()
    {
        pawn=null;
    }

    public Block getBlock(){return typeOfBuilding;}

    //TODO: setTypeOfBuilding fa quello che dovrebbe fare build FATTO
    public void build(Block b) {
        this.typeOfBuilding = b;
    }


    //TODO: isFree controlla che pawn!=null    FATTO
    public boolean isFree()
    {
        if(pawn==null)
            return true;
        return false;
    }

    //TODO: canBuild controlla che typeOfBuilding!=DOME  FATTO
    public boolean canBuild(Block block)
    {
        if(typeOfBuilding!=DOME)
            return true;
        return false;
    }

    public boolean compare(Box other){
        try{
            if((this.typeOfBuilding.getValue() >= other.typeOfBuilding.getValue() || (this.typeOfBuilding.getValue() - 1) == other.typeOfBuilding.getValue()) && other.typeOfBuilding.getValue() != 4){
                return true;
            }
            else{
                return false;
            }
        }
        catch(IllegalArgumentException e){return false;}
    }
}
