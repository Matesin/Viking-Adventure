package item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;

@Getter
@Setter
public class LevelKey extends Item{
    private int level;
    private int id;
    @JsonCreator
    public LevelKey(@JsonProperty("name") String name, @JsonProperty("level") int level, @JsonProperty("key_id") int id, @JsonProperty("x") int worldCoordX, @JsonProperty("y") int worldCoordY) throws FileNotFoundException {
        this.name = name;
        this.level = level;
        this.id = id;
        this.worldCoordX = worldCoordX;
        this.worldCoordY = worldCoordY;
        loadImage(level + "_key_" + this.id);
    }
}
