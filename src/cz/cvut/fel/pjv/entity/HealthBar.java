package cz.cvut.fel.pjv.entity;

import javafx.scene.control.ProgressBar;
import lombok.Getter;

/**
 * Class representing the health bar of a character.
 */
public class HealthBar {
    private int health;
    private final int maxHealth;
    private final Character entity;
    @Getter
    private ProgressBar healthProgressBar;

    public HealthBar(Character entity, int maxHealth){
        this.health = entity.getHealth();
        this.entity = entity;
        this.maxHealth = maxHealth;
    }
    public void update(){
        this.health = entity.getHealth();
        healthProgressBar.setProgress((double) health / maxHealth);
    }
    public void init(){
        healthProgressBar = new ProgressBar();
        healthProgressBar.setProgress((double) health / maxHealth);
    }
    public void display(){
        if (entity instanceof Player){
            healthProgressBar.setLayoutX(10);
            healthProgressBar.setLayoutY(10);
        }
        else{
            healthProgressBar.setLayoutX(entity.getScreenCoordX());
            healthProgressBar.setLayoutY(entity.getScreenCoordY() - 10);
        }
    }
}
