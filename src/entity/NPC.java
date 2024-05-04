package entity;

public abstract class NPC extends Character{

    protected NPC(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
        this.hitbox = new Hitbox(this, this.width, this.height, 0, 0);
    }

    public void idleMovement(){
        // NPC will move around randomly in a small area
    }
}
