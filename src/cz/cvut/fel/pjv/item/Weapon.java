package cz.cvut.fel.pjv.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Weapon extends Item {
    int damage;
    int range;
    @JsonCreator
    protected Weapon(@JsonProperty("x") int worldCoordX,
                     @JsonProperty("y") int worldCoordY,
                     @JsonProperty("picture") String pictureID) {
        super(worldCoordX, worldCoordY, pictureID);
    }
}
