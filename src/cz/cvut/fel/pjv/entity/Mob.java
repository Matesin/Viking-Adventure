package cz.cvut.fel.pjv.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Mob extends Enemy{
    private String type; //for sprite loading purposes

    @JsonCreator
    public Mob(@JsonProperty("x") int worldCoordX, @JsonProperty("y") int worldCoordY) {
        super(worldCoordX, worldCoordY);
        this.hp = 100;
        this.reactionRange = 50;
        this.attackRange = 10;
    }
}
