package entity;

public abstract class TaskAuthority extends RespondingNPC{
    protected TaskAuthority(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
        this.hitbox = new Hitbox(this, this.width, this.height, 0, 0);
    }
}
