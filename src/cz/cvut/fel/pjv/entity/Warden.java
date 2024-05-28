package cz.cvut.fel.pjv.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * Class representing a warden non-player character (NPC) in the game.
 * This class extends the TaskAuthority class and represents a specific type of task authority NPC.
 */
@Slf4j
public class Warden extends Enemy{
    /**
     * Constructor for Warden with specified world coordinates.
     *
     * @param worldCoordX the x-coordinate of the NPC in the world
     * @param worldCoordY the y-coordinate of the NPC in the world
     */
    @JsonCreator
    protected Warden(@JsonProperty("x") int worldCoordX, @JsonProperty("y") int worldCoordY) {
        super(worldCoordX, worldCoordY);
    }
}