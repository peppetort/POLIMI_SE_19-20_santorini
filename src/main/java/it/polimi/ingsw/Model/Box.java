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

    //TODO: fare metodo removePawn():void

    public Block getBlock(){return typeOfBuilding;}

    //TODO: setTypeOfBuilding fa quello che dovrebbe fare build
    public void setTypeOfBuilding(Block typeOfBuilding) {
        this.typeOfBuilding = typeOfBuilding;
    }
    public boolean build(Block b)
    {
        return true;
    }

    //TODO: isFree controlla che pawn!=null
    public boolean isFree()
    {
        return true;
    }

    //TODO: canBuild controlla che typeOfBuilding!=DOME
    public boolean canBuild(Block block)
    {
        return true;
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
