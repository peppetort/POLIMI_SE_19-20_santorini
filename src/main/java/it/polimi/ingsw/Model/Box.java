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

    public void setPawn(Worker pawn) //throws RuntimeException
    {

     //   if (this.pawn == null)
            this.pawn=pawn;
     //   else
      //      throw new RuntimeException("This box is not empty");
    }

    public void removePawn()
    {
        pawn=null;
    }

    public Block getBlock(){return typeOfBuilding;}

    public void build(Block b) {
        this.typeOfBuilding = b;
    }

    public boolean isFree()
    {
        return pawn == null;
    }

    public boolean canBuild(Block block)
    {
        return typeOfBuilding != Block.DOME;
    }

    public int getDifference(Box other){
        return this.typeOfBuilding.getValue()-other.typeOfBuilding.getValue();
    }

    public boolean compare(Box other){

        return this.getDifference(other) >= 0 || (this.getDifference(other) == -1 && other.typeOfBuilding.getValue() != 4);

    }
}
