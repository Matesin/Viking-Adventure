package entity;

public abstract class NPC extends Character{

    protected NPC(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }

    public void idleMovement(){
        // NPC will move around randomly in a small area
    }
}
