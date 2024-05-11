package cz.cvut.fel.pjv.entity;

public abstract class Enemy extends RespondingNPC{
    int attackDamage;
    boolean isTriggered;
    boolean isInAttackRange;
    int reactionRange;
    int attackRange;

    protected Enemy(int worldCoordX, int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }


    public void attack() {
        // Attack the res.player if they are in range
    }
    public void takeDamage(int damage) {
        // Take damage from the res.player
    }
    public void die() {
        // Remove the enemy from the game
    }
    public void trigger() {
        // Trigger the enemy to start attacking
    }
}
