package cz.cvut.fel.pjv.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;

/**
 * Class representing a key item.
 */
@Getter
@Setter
public class LevelKey extends Item {
    private int level;
    private int id;
    @JsonCreator
    public LevelKey(@JsonProperty("name") String name,
                    @JsonProperty("level") int level,
                    @JsonProperty("key_id") int id,
                    @JsonProperty("x") int worldCoordX,
                    @JsonProperty("y") int worldCoordY,
                    @JsonProperty("picture") String pictureID) throws FileNotFoundException {
        super(worldCoordX, worldCoordY, pictureID);
        this.name = name;
        this.level = level;
        this.id = id;
        loadImage(level + "_key_" + this.id);
    }
}
