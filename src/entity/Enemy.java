package entity;

public abstract class Enemy extends RespondingNPC{
    int attackDamage;
    private boolean isTriggered;
    private boolean isInAttackRange;
    public int reactionRange;
    public int attackRange;
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
