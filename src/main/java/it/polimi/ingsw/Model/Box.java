package it.polimi.ingsw.Model;

/**
 * Represents the cell where I can build/move or place a {@link Worker}.
 */
public class Box {

    /**
     * This contains a type {@link Block}
     */
    private Block typeOfBuilding;
    /**
     * It contains a reference to the {@link Worker} placed on this. It can be null.
     */
    private Worker pawn;

    /**
     * Constructor of the class. The type is setted at TERRAIN level with no pawn.
     */
    public Box() {
        typeOfBuilding = Block.TERRAIN;
        pawn = null;
    }


    public Worker getPawn() {
        return pawn;
    }


    public void setPawn(Worker pawn) {
        this.pawn = pawn;
    }

    /**
     * Remove the {@link Worker} from the {@link Box}.
     */
    public void removePawn() {
        pawn = null;
    }


    public Block getBlock() {
        return typeOfBuilding;
    }

    /**
     * @param b setter for the typeOfBuilding attribute.
     */
    public void build(Block b) {
        this.typeOfBuilding = b;
    }

    /**
     * @return true if the {@link Box} has no {@link Worker} on itself.
     */
    public boolean isFree() {
        return pawn == null;
    }

    /**
     * @param other cella sulla quale è calcolata la differenza
     * @return la differenza di livello tra la costruzione attuale e quella passata
     */
    public int getDifference(Box other) {
        return this.typeOfBuilding.getValue() - other.typeOfBuilding.getValue();
    }

    /**
     * @param other
     * @return true if I can move from this {@link Box} to other.
     */
    public boolean compare(Box other) {
        return this.getDifference(other) >= -1 && other.typeOfBuilding.getValue() != 4;
    }
}
