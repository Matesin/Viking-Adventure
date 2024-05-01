package item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.FileNotFoundException;

public class WeaponUpgrade extends Item{
    String upgradeType;
    int upgradeValue;
    @JsonCreator
    public WeaponUpgrade(@JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("upgrade_type")String upgradeType, @JsonProperty("upgrade_value")int upgradeValue,@JsonProperty("x") int worldCoordX,@JsonProperty("y") int worldCoordY) throws FileNotFoundException {
        this.name = name;
        this.description = description;
        this.upgradeType = upgradeType;
        this.upgradeValue = upgradeValue;
        this.worldCoordX = worldCoordX;
        this.worldCoordY = worldCoordY;
        loadImage(name);
    }
}
