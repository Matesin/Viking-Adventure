package cz.cvut.fel.pjv.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a mob in the game.
 * This class extends the Enemy class and represents a specific type of enemy.
 */
public class Mob extends Enemy{
    /**
     * Constructor for Mob with specified world coordinates.
     *
     * @param worldCoordX the x-coordinate of the mob in the world
     * @param worldCoordY the y-coordinate of the mob in the world
     */
    @JsonCreator
    public Mob(@JsonProperty("x") int worldCoordX, @JsonProperty("y") int worldCoordY) {
        super(worldCoordX, worldCoordY);
        this.hp = 100;
    }
}