package item;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.FileNotFoundException;

public class RangedWeapon extends Weapon{
    public RangedWeapon(@JsonProperty("name")String name,
                        @JsonProperty("damage") int damage,
                        @JsonProperty("range") int range,
                        @JsonProperty("description")String description,
                        @JsonProperty("x") int worldCoordX,
                        @JsonProperty("y") int worldCoordY,
                        @JsonProperty("picture") String pictureID) {
        super(worldCoordX, worldCoordY, pictureID);
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.description = description;
    }

}
