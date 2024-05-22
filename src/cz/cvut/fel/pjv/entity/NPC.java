package cz.cvut.fel.pjv.entity;

/**
 * Abstract class representing a non-player character (NPC) in the game.
 * This class extends the Character class and can be extended by specific types of NPCs.
 */
public abstract class NPC extends Character{

    /**
     * Constructor for NPC with specified world coordinates.
     *
     * @param worldCoordX the x-coordinate of the NPC in the world
     * @param worldCoordY the y-coordinate of the NPC in the world
     */
    protected NPC(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }

    /**
     * Handles the idle movement of the NPC.
     * The NPC will move around randomly in a small area.
     */
    public void idleMovement(){
        // NPC will move around randomly in a small area
    }
}