package cz.cvut.fel.pjv.entity;

/**
 * Abstract class representing a task authority non-player character (NPC) in the game.
 * This class extends the RespondingNPC class and can be extended by specific types of task authority NPCs.
 */
public abstract class TaskAuthority extends RespondingNPC{

    /**
     * Constructor for TaskAuthority with specified world coordinates.
     *
     * @param worldCoordX the x-coordinate of the NPC in the world
     * @param worldCoordY the y-coordinate of the NPC in the world
     */
    protected TaskAuthority(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }
}