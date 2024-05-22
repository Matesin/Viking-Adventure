package cz.cvut.fel.pjv.entity;

/**
 * Abstract class representing an enemy in the game.
 * This class is extended by specific types of enemies.
 */
public abstract class Enemy extends RespondingNPC{
    int attackDamage;
    boolean isTriggered;
    boolean isInAttackRange;
    int reactionRange;
    int attackRange;

    /**
     * Constructor for Enemy.
     *
     * @param worldCoordX the x-coordinate of the enemy in the world
     * @param worldCoordY the y-coordinate of the enemy in the world
     */
    protected Enemy(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }

    /**
     * Attacks the player if they are in range.
     */
    public void attack() {
        // Attack the res.player if they are in range
    }

    /**
     * Takes damage from the player.
     *
     * @param damage the amount of damage to take
     */
    public void takeDamage(int damage) {
        // Take damage from the res.player
    }

    /**
     * Removes the enemy from the game.
     */
    public void die() {
        // Remove the enemy from the game
    }

    /**
     * Triggers the enemy to start attacking.
     */
    public void trigger() {
        // Trigger the enemy to start attacking
    }
}