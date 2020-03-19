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

    public Block getTypeOfBuilding(){return typeOfBuilding;}
    public void setTypeOfBuilding(Block typeOfBuilding) {
        this.typeOfBuilding = typeOfBuilding;
    }

    public boolean build(Block b)
    {
        return true;
    }
    public boolean isFree()
    {
        return true;
    }
    public boolean canBuild(Block b)
    {
        return true;
    }
}
