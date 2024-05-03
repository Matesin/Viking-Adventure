package item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.FileNotFoundException;

public class MeleeWeapon extends Weapon{
    @JsonCreator
    public MeleeWeapon(@JsonProperty("name") String name,
                       @JsonProperty("damage") int damage,
                       @JsonProperty("description") String description,
                       @JsonProperty("x")int worldCoordX,
                       @JsonProperty("y")int worldCoordY,
                       @JsonProperty("picture") String pictureID) {
        super(worldCoordX, worldCoordY, pictureID);
        this.name = name;
        this.damage = damage;
        this.description = description;
        this.worldCoordX = worldCoordX;
        this.worldCoordY = worldCoordY;
    }

}
