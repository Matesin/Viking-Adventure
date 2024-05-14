package cz.cvut.fel.pjv.entity;

import javafx.scene.control.ProgressBar;

import javax.swing.text.html.parser.Entity;

public class HealthBar {
    private int health;
    private int maxHealth;
    private Character entity;
    public ProgressBar healthBar;

    public HealthBar(Character entity, int maxHealth){
        this.health = entity.getHealth();
        this.entity = entity;
        this.maxHealth = maxHealth;
    }
    public void update(){
        this.health = entity.getHealth();
        healthBar.setProgress((double) health / maxHealth);
    }
    public void init(){
        healthBar = new ProgressBar();
        healthBar.setProgress((double) health / maxHealth);
    }
    public void display(){
        if (entity instanceof Player){
            healthBar.setLayoutX(10);
            healthBar.setLayoutY(10);
        }
        else{
            healthBar.setLayoutX(entity.getScreenCoordX());
            healthBar.setLayoutY(entity.getScreenCoordY() - 10);
        }
    }
}
