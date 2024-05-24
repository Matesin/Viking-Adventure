package cz.cvut.fel.pjv.entity;

/**
 * Abstract class representing an enemy in the game.
 * This class is extended by specific types of enemies.
 */
public abstract class Enemy extends RespondingNPC{
    int attackDamage;
    boolean isTriggered;
    boolean isInAttackRange;
    final Hitbox reactionRange;
    final Hitbox attackRange;
    /**
     * Constructor for Enemy.
     *
     * @param worldCoordX the x-coordinate of the enemy in the world
     * @param worldCoordY the y-coordinate of the enemy in the world
     */
    protected Enemy(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
        this.reactionRange = new Hitbox(this, this.hitbox.getWidth() * 5, this.hitbox.getHeight() * 5, 0, 0);
        this.attackRange = new Hitbox(this, this.hitbox.getWidth() * 2, this.hitbox.getHeight() * 2, 0, 0);
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
    private void trigger(Player player) {
        // Trigger the enemy to start attacking
        if (reactionRange.intersects(player.hitbox)) {
            isTriggered = true;
        } else {
            isTriggered = false;
            idleMovement();
        }
        if (isTriggered) {
            moveTowardsPlayer(player);
            if (player.hitbox.intersects(attackRange)) {
                attack();
            }
        }
    }
    private void moveTowardsPlayer(Player player) {
        // Move towards the res.player
        // Calculate the direction vector from the enemy to the player
        double dx = player.getWorldCoordX() - this.getWorldCoordX();
        double dy = player.getWorldCoordY() - this.getWorldCoordY();

        // Normalize the direction vector to get a unit vector
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double dirX = dx / magnitude;
        double dirY = dy / magnitude;

        // Move the enemy along the direction vector
        this.setWorldCoordX(this.getWorldCoordX() + dirX);
        this.setWorldCoordY(this.getWorldCoordY() + dirY);
    }

}